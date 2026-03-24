package br.com.Restaurant.Management.API.usersType.infra.controller;

import br.com.Restaurant.Management.API.users.infra.gateway.config.SecurityConfigurationsTest;
import br.com.Restaurant.Management.API.usersType.core.dto.input.CreateUserTypeInputDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class CreateUserTypeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "api/v1/userstype";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 201 when user type is created successfully")
    void shouldReturnCreatedWhenUserTypeIsValid() {
        var request = new CreateUserTypeInputDTO("ADMIN");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", equalTo("ADMIN"))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("should return 409 when user type name already exists")
    void shouldReturnConflictWhenNameExists() {
        var request = new CreateUserTypeInputDTO("CLIENTE");

        given().contentType(ContentType.JSON)
                .body(request)
                .post();

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(409)
                .body("code", equalTo("USER_TYPE_ALREADY_IN_USE"));
    }
}
