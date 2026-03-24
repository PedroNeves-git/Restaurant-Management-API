package br.com.Restaurant.Management.API.menuitems.infra.controller;


import br.com.Restaurant.Management.API.menuItems.core.dto.input.CreateMenuItemInputDTO;
import br.com.Restaurant.Management.API.users.infra.gateway.config.SecurityConfigurationsTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
public class CreateMenuItemControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/menu-items";
    }

    @Test
    @DisplayName("should return 201 when menu item is created successfully")
    void shouldReturnCreatedWhenMenuItemIsValid() {

    }

    @Test
    @DisplayName("should return 500 when domain validation fails (Current behavior)")
    void shouldReturn500WhenValidationFails() {

    }
}