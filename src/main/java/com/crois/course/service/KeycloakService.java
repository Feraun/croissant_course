package com.crois.course.service;

import com.crois.course.dto.UserDTO.Login.UserLoginRequestDTO;
import com.crois.course.dto.UserDTO.Login.UserLoginResponse;
import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.entity.UserEntity;
import com.crois.course.enums.Role;
import com.crois.course.mapper.UserMapper;
import com.crois.course.properties.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final HelperService helperService;

    private final UserMapper mapper;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String backendClientId;

    private final RestTemplate restTemplate = new RestTemplate();

    private final Keycloak keycloakAdmin;

    private final KeycloakProperties properties;

    public UserLoginResponse login(UserLoginRequestDTO request) {
        String tokenUrl = issuerUri + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "password");
        map.add("username", request.getUsername());
        map.add("password", request.getPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, entity, Map.class);

        Map<String, Object> body = response.getBody();
        return UserLoginResponse.builder()
                .accessToken((String) body.get("access_token"))
                .refreshToken((String) body.get("refresh_token"))
                .expiresIn(((Number) body.get("expires_in")).longValue())
                .tokenType((String) body.get("token_type"))
                .build();
    }

    public void register(UserRegistrationRequestDTO request) {

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloakAdmin.realm(properties.getTargetRealm()).users().create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Ошибка регистрации в Keycloak. Статус: " + response.getStatus());
        }

        String path = response.getLocation().getPath();
        String keycloakIdStr = Paths.get(path).getFileName().toString();

        UserEntity currentUser = mapper.toUserRegistrationEntity(request, keycloakIdStr);

        try {
            helperService.saveUser(currentUser);
        }
        catch (Exception e) {
            keycloakAdmin.realm(properties.getTargetRealm()).users().get(keycloakIdStr).remove();
            throw new RuntimeException("Ошибка сохранения в локальную БД. Пользователь из Keycloak удален.", e);
        }
    }

    public void promoteClientToManager(String keycloakUserId) {

        RealmResource realmResource = keycloakAdmin.realm(properties.getTargetRealm());
        UserResource userResource = realmResource.users().get(keycloakUserId);

        // 1. Находим внутренний ID клиента (он отличается от названия tasty_hour_backend)
        ClientRepresentation clientRep = realmResource.clients().findByClientId(backendClientId).getFirst();
        String internalClientId = clientRep.getId();
        ClientResource clientResource = realmResource.clients().get(internalClientId);

        // 2. Получаем представления ролей
        RoleRepresentation roleClient = clientResource.roles().get(Role.CLIENT.toString()).toRepresentation();
        RoleRepresentation roleManager = clientResource.roles().get(Role.MANAGER.toString()).toRepresentation();

        // 3. Забираем CLIENT и выдаем MANAGER
        userResource.roles().clientLevel(internalClientId).remove(Collections.singletonList(roleClient));
        userResource.roles().clientLevel(internalClientId).add(Collections.singletonList(roleManager));
    }

    public void demoteManagerToClient(String keycloakUserId) {
        RealmResource realmResource = keycloakAdmin.realm(properties.getTargetRealm());
        UserResource userResource = realmResource.users().get(keycloakUserId);

        ClientRepresentation clientRep = realmResource.clients().findByClientId(backendClientId).getFirst();
        String internalClientId = clientRep.getId();
        ClientResource clientResource = realmResource.clients().get(internalClientId);

        RoleRepresentation roleClient = clientResource.roles().get(Role.CLIENT.toString()).toRepresentation();
        RoleRepresentation roleManager = clientResource.roles().get(Role.MANAGER.toString()).toRepresentation();

        // Забираем MANAGER и выдаем CLIENT
        userResource.roles().clientLevel(internalClientId).remove(Collections.singletonList(roleManager));
        userResource.roles().clientLevel(internalClientId).add(Collections.singletonList(roleClient));
    }
}
