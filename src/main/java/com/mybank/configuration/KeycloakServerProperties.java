package com.mybank.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "keycloak") // reads settings from file
public class KeycloakServerProperties {
    private String appRealm;                  // settings are in file (names must be equal)
    private String idmBrokerAuthUrl;
    private String idmBrokerApp;
    private KeycloakServer server;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KeycloakServer {
        private String contextPath;
        private AdminUser adminUser = new AdminUser();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminUser {
        private String username;
        private String password;
    }
}
