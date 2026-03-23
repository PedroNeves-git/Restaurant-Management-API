package br.com.Restaurant.Management.API.cuisinetype.infra.controller;

import br.com.Restaurant.Management.API.cuisinetype.core.dto.input.CreateCuisineTypeInputDTO;
import br.com.Restaurant.Management.API.users.infra.gateway.config.SecurityConfigurationsTest;
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
class CreateCuisineTypeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/cuisinetype";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 201 when cuisine type is created successfully")
    void shouldReturnCreatedWhenCuisineTypeIsValid() {
        var request = new CreateCuisineTypeInputDTO("Italiana");

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Italiana"))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("should return 500 when cuisine type name is blank (Current behavior)")
    void shouldReturnInternalErrorWhenNameIsInvalid() {
        var request = new CreateCuisineTypeInputDTO("");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(500)
                .contentType(ContentType.JSON)
                .body("status", equalTo(500))
                .body("code", equalTo("INTERNAL_ERROR"))
                .body("message", equalTo("Erro interno do servidor"));
    }
}