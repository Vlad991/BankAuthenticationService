package com.mybank.configuration.impl;

import com.mybank.configuration.KeycloakConnection;
import com.mybank.configuration.KeycloakServerProperties;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Qualifier("broker")
public class KeycloakConnectionImpl implements KeycloakConnection {
    private KeycloakServerProperties properties;
    private static volatile Keycloak INSTANCE;

    public KeycloakConnectionImpl(KeycloakServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public Keycloak getKeycloakClient() {
        if (INSTANCE == null) {
            synchronized (KeycloakConnectionImpl.class) {  // not to be two connection instances created
                INSTANCE = KeycloakBuilder.builder()
                        .serverUrl(properties.getIdmBrokerAuthUrl())
                        .realm("master")  // todo ?????
                        .grantType(OAuth2Constants.PASSWORD)
                        .clientId(properties.getIdmBrokerApp())
                        .username(properties.getServer().getAdminUser().getUsername()) // name and password to connect to keycloak
                        .password(properties.getServer().getAdminUser().getPassword())
                        .build();
            }
        }
        return INSTANCE;
    }

    @PreDestroy
    public void close() {
        if (INSTANCE != null) {
            INSTANCE.close();
        }
    }
}
