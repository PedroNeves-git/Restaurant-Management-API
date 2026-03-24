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

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListUserTypeByIdControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 and user type when ID exists")
    void shouldReturnUserTypeWhenIdExists() {
        String uniqueName = "COZINHEIRO_" + UUID.randomUUID().toString().substring(0, 8);
        var createRequest = new CreateUserTypeInputDTO(uniqueName);

        var idCriado = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/v1/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        given().pathParam("id", idCriado)
                .when()
                .get("/api/v1/userstype/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(idCriado))
                .body("name", equalTo(uniqueName));
    }

    @Test
    @DisplayName("should return 404 when user type ID does not exist")
    void shouldReturn404WhenIdDoesNotExist() {
        given()
                .pathParam("id", 9999)
                .when()
                .get("/api/v1/userstype/{id}")
                .then()
                .statusCode(404)
                .body("code", equalTo("USER_TYPE_NOT_FOUND"));
    }
}