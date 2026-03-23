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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class DeleteCuisineTypeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/cuisinetype";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 when cuisine type is created and then deleted")
    void shouldReturnOkWhenDeleteIsSuccessful() {
        var createRequest = new CreateCuisineTypeInputDTO("Japonesa");
        var response = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post() // CreateCuisineTypeController
                .then()
                .statusCode(201)
                .extract().response();

        var idParaDeletar = Long.valueOf(response.path("id").toString());

        given()
                .pathParam("id", idParaDeletar)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .body("code", equalTo("CUISINETYPE_DELETED"));
    }

}