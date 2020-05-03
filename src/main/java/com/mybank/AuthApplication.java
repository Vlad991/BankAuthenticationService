package com.mybank;

import com.mybank.configuration.KeycloakServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties(KeycloakServerProperties.class)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent>
    onApplicationReadyEventListener(ServerProperties serverProperties,
                                    KeycloakServerProperties keycloakServerProperties) {

        return (evt) -> {
            Integer port = serverProperties.getPort();
            String rootContextPath = serverProperties.getServlet().getContextPath();
            if (rootContextPath == null) {
                rootContextPath = "";
            }
            String keycloakContextPath = keycloakServerProperties.getServer().getContextPath();

            System.out.printf("Embedded Keycloak started: http://someHost:%s%s%s to use keycloak%n",
                    port,
                    rootContextPath,
                    keycloakContextPath);
        };
    }
}
