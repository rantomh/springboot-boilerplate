package com.rantomah.boilerplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@Testcontainers
public class ServiceTest {

    @Autowired private MockMvc mockMvc;

    @Value("${application.security.oidc.default-user}")
    private String defaultUser;

    @Value("${application.security.oidc.default-password}")
    private String defaultPassword;

    @Container
    static final PostgreSQLContainer<?> psqlContainer =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("test_db")
                    .withUsername("dbuser")
                    .withPassword("dbpassword");

    static {
        psqlContainer.start();
        System.setProperty("spring.datasource.url", psqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", psqlContainer.getUsername());
        System.setProperty("spring.datasource.password", psqlContainer.getPassword());
    }

    @Test
    void shouldReturnUser() throws Exception {
        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType("application/json")
                                .content(
                                        String.format(
                                                """
                                {
                                    "username": "%s",
                                    "password": "%s"
                                }
                                """,
                                                defaultUser, defaultPassword)))
                .andExpect(status().isOk());
    }
}
