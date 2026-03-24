package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.input.UpdateUserInputDTO;
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
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
@TestPropertySource(properties = "api.security.token.secret=minha-senha-secreta-de-teste")
class UpdateUserControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private Long createTestUserType(String roleName) {
        return given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + roleName + "\"}")
                .when()
                .post("/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    private Long createTestUser(String name, String email, String login, Long typeId) {
        var request = new CreateUserInputDTO(
                name,
                email,
                login,
                "senha123",
                typeId,
                UserRole.RESTAURANT_OWNER
        );

        return given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    @Test
    @DisplayName("should return 200 when user is successfully updated")
    void shouldUpdateUserSuccessfully() {
        String unique = UUID.randomUUID().toString().substring(0, 8);

        Long typeId = createTestUserType("ROLE_UPDATE_" + unique);
        Long userId = createTestUser("Usuário Antigo", "old_" + unique + "@email.com", "old.login." + unique, typeId);

        var updateRequest = new UpdateUserInputDTO(
                "Usuário Atualizado",
                "new_" + unique + "@email.com",
                "new.login." + unique,
                typeId,
                true
        );

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", userId)
                .body(updateRequest)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Usuário Atualizado"))
                .body("email", equalTo("new_" + unique + "@email.com"));
    }

    @Test
    @DisplayName("should return 404 when trying to update a non-existent user")
    void shouldReturn404WhenUserNotFound() {
        Long typeId = createTestUserType("ROLE_NOT_FOUND_" + UUID.randomUUID().toString().substring(0, 8));

        var updateRequest = new UpdateUserInputDTO(
                "Usuário Fantasma",
                "fantasma@email.com",
                "fantasma.login",
                typeId,
                true
        );

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 999999L)
                .body(updateRequest)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("should return 409 when attempting to create a user with a login already in use")
    void shouldReturn400WhenLoginAlreadyInUse() {
        var uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        var duplicateLogin = "user.duplicate." + uniqueSuffix;
        var typeId = createTestUserType("ROLE_DUP_" + uniqueSuffix);

        createTestUser(
                "Primeiro Usuario",
                "primeiro_" + uniqueSuffix + "@email.com",
                duplicateLogin,
                typeId);

        var duplicateRequest = new CreateUserInputDTO(
                "Segundo Usuario",
                "segundo_" + uniqueSuffix + "@email.com",
                duplicateLogin,
                "OutraSenha123",
                typeId,
                UserRole.RESTAURANT_OWNER
        );

        given().contentType(ContentType.JSON)
                .body(duplicateRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(409)
                .body("code", equalTo("LOGIN_ALREADY_IN_USE"))
                .body("message", containsString("Login already exists"));
    }
}