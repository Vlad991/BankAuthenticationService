package com.mybank.service;

import com.mybank.configuration.KeycloakConnection;
import com.mybank.exception.ValidationException;
import com.mybank.messaging.dto.UserDTO;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private KeycloakConnection keycloakConnection;
    private Validator validator;

    public UserService(KeycloakConnection keycloakConnection, Validator validator) {
        this.keycloakConnection = keycloakConnection;
        this.validator = validator;
    }

    public void insert(UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> errors = validator.validate(userDTO);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors.iterator().next().getMessage());
        }

        UserRepresentation userRepresentation = new UserRepresentation();  // create keycloak user
        userRepresentation.setEnabled(true);                 // user is active, that means we can get token on his name
        userRepresentation.setUsername(userDTO.getLogin());

        keycloakConnection.getKeycloakClient().realm("bank").users().create(userRepresentation); // REST request
        UsersResource usersResource = keycloakConnection.getKeycloakClient().realm("bank").users();
        List<UserRepresentation> userRepresentationList = usersResource.list(); // getting all users to create password
        Optional<UserRepresentation> user = userRepresentationList.stream()
                .filter(userRep -> userRep.getUsername().equals(userDTO.getLogin()))
                .findFirst();
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType("password");
        credentialRepresentation.setValue(userDTO.getPassword());

        user.ifPresent(u -> {
            keycloakConnection.getKeycloakClient()
                    .realm("bank")
                    .users()
                    .get(u.getId())
                    .resetPassword(credentialRepresentation);

            ClientRepresentation clientRepresentation = keycloakConnection.getKeycloakClient().realm("bank")
                    .clients()
                    .findAll()
                    .stream()
                    .filter(client -> client.getClientId().equals("bank-web"))
                    .findFirst().get();

            List<RoleRepresentation> roleRepresentations = keycloakConnection.getKeycloakClient().realm("bank")
                    .users().get(u.getId()).roles().clientLevel(clientRepresentation.getId()).listAvailable();

            RoleRepresentation role = roleRepresentations.stream()
                    .filter(roleRep -> roleRep.getName().equals("ROLE_CLIENT"))
                    .findFirst().get();

            keycloakConnection.getKeycloakClient()
                    .realm("bank")
                    .users().get(u.getId())
                    .roles().clientLevel(clientRepresentation.getId())
                    .add(Arrays.asList(role));
        });
    }
}
