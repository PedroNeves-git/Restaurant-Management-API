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
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class CreateUserControllerTest {

    @LocalServerPort
    private int port;

    private Long userTypeId;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 201 when user is created successfully")
    void shouldReturnCreatedWhenUserIsValid() {
            var userTypeId = given()
                    .contentType(ContentType.JSON)
                    .body("{\"name\":\"RESTAURANT_OWNER\"}")
                    .when()
                    .post("/userstype")
                    .then()
                    .statusCode(201)
                    .extract().jsonPath().getLong("id");
            var request = new CreateUserInputDTO(
                    "Carlos Silva",
                    "carlos@email.com",
                    "carlos.dev",
                    "senha123",
                    userTypeId,
                    UserRole.RESTAURANT_OWNER
            );

            given().contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post("/users")
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("name", equalTo("Carlos Silva"));
        }

    @Test
    @DisplayName("should return 409 when email is already in use")
    void shouldReturnConflictWhenEmailExists() {
        Long userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"RESTAURANT_OWNER_CONFLICT\"}")
                .when()
                .post("/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        var request = new CreateUserInputDTO(
                "Carlos Silva",
                "duplicado@email.com",
                "login.unico",
                "senha123",
                userTypeId,
                UserRole.RESTAURANT_OWNER
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users")
                .then()
                .statusCode(409)
                .body("code", equalTo("EMAIL_ALREADY_IN_USE"));
    }
}