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
import static org.hamcrest.Matchers.is;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListCuisineTypeByIdControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/cuisinetype";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 and correct cuisine type when id exists")
    void shouldReturnCuisineTypeWhenIdExists() {
        var createRequest = new CreateCuisineTypeInputDTO("Francesa");

        var response = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().response();

        var idCadastrado = response.jsonPath().getInt("id");

        given()
                .pathParam("id", idCadastrado)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idCadastrado))
                .body("name", equalTo("Francesa"));
    }

    @Test
    @DisplayName("should return 500 when cuisine type id does not exist (Current behavior)")
    void shouldReturnInternalErrorWhenIdNotFound() {
        var idInexistente = 99L;

        given()
                .pathParam("id", idInexistente)
                .when()
                .get("/{id}")
                .then()
                .statusCode(500)
                .body("code", equalTo("INTERNAL_ERROR"));
    }
}