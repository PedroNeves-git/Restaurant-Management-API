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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class DeleteUserControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 when user is deleted successfully")
    void shouldDeleteUserSuccessfully() {
        int userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"DELETE_ROLE\"}")
                .when()
                .post("/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        var userRequest = new CreateUserInputDTO(
                "Para Deletar",
                "delete@email.com",
                "login.delete",
                "123456",
                Long.valueOf(userTypeId),
                UserRole.RESTAURANT_OWNER
        );

        int userId = given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        given().pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(200)
                .body("code", equalTo("USER_DELETED"))
                .body("message", equalTo("User deleted successfully"));
    }

    @Test
    @DisplayName("should return 404 when user to delete does not exist")
    void shouldReturn404WhenNotFound() {
        given()
                .pathParam("id", 9999)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(404)
                .body("code", equalTo("USER_NOT_FOUND"));
    }
}