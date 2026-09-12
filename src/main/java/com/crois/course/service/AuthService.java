package com.crois.course.service;

import com.crois.course.dto.UserDTO.Login.UserLoginRequestDTO;
import com.crois.course.dto.UserDTO.Login.UserLoginResponse;
import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.entity.UserEntity;
import com.crois.course.mapper.UserMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
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
public class AuthService {

    private final HelperService helperService;

    private final UserMapper mapper;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

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
        // Подключаемся к Keycloak с правами администратора
        Keycloak keycloakAdmin = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081") // Базовый URL Keycloak
                .realm("master") // Админская реама
                .username("admin") // Ваша учетка админа Keycloak
                .password("admin") // Пароль админа
                .clientId("admin-cli")
                .build();

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

        Response response = keycloakAdmin.realm("Oauth").users().create(user);

        String path = response.getLocation().getPath();
        String keycloakIdStr = Paths.get(path).getFileName().toString();

        if (response.getStatus() != 201) {
            throw new RuntimeException("Ошибка регистрации в Keycloak. Статус: " + response.getStatus());
        }

        UserEntity currentUser = mapper.toUserRegistrationEntity(request, keycloakIdStr);

        try {
            helperService.saveUser(currentUser);
        }
        catch (Exception e) {
            keycloakAdmin.realm("Oauth").users().get(keycloakIdStr).remove();
            throw new RuntimeException("Ошибка сохранения в локальную БД. Пользователь из Keycloak удален.", e);
        }
    }
}
