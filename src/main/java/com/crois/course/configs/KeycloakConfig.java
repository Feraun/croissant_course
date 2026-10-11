package com.crois.course.configs;

import com.crois.course.properties.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final KeycloakProperties properties;

    @Bean
    public Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
            .serverUrl(properties.getServerUri())
            .realm(properties.getAdminRealm())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .clientId(properties.getClientId())
            .build();
    }
}
