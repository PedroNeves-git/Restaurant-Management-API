package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
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
class ListUserByIdControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 and user when ID exists")
    void shouldReturnUserWhenIdExists() {
        int userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"USER_BY_ID_ROLE\"}")
                .when()
                .post("api/v1/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        var userRequest = new CreateUserInputDTO(
                "Busca Por ID",
                "buscaid@email.com",
                "login.buscaid",
                "123456",
                (long) userTypeId,
                UserRole.RESTAURANT_OWNER
        );

        int userId = given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post("api/v1/users")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        given().pathParam("id", userId)
                .when()
                .get("api/v1/users/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(userId))
                .body("name", equalTo("Busca Por ID"))
                .body("email", equalTo("buscaid@email.com"));
    }

    @Test
    @DisplayName("should return 404 when user ID does not exist")
    void shouldReturn404WhenNotFound() {
        given().pathParam("id", 9999)
                .when()
                .get("api/v1/users/{id}")
                .then()
                .statusCode(404)
                .body("code", equalTo("USER_NOT_FOUND"));
    }
}