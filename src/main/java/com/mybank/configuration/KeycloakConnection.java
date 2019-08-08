package com.mybank.configuration;

import org.keycloak.admin.client.Keycloak;

public interface KeycloakConnection {
    Keycloak getKeycloakClient();
}
