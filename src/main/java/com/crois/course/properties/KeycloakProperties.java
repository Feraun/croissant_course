package com.crois.course.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.keycloak")
public class KeycloakProperties {

    private String serverUri;

    private String adminRealm;

    private String targetRealm;

    private String username;

    private String password;

    private String clientId;

}
