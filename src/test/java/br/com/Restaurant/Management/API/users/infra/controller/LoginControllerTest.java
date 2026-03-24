package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.LoginDTO;
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
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
@TestPropertySource(properties = "api.security.token.secret=minha-senha-secreta-de-teste") // Previne o erro do TokenService!
class LoginControllerTest {

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

    private void createTestUser(String name, String email, String login, Long typeId, String password) {
        var request = new CreateUserInputDTO(
                name,
                email,
                login,
                password,
                typeId,
                UserRole.RESTAURANT_OWNER
        );

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("should return 200 and a JWT token when login is successful")
    void shouldReturn200AndTokenWhenLoginIsSuccessful() {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        String loginStr = "login.auth." + unique;
        String passwordStr = "senhaSuperSecreta123";

        Long typeId = createTestUserType("ROLE_AUTH_" + unique);
        createTestUser("Usuário de Login", "auth_" + unique + "@email.com", loginStr, typeId, passwordStr);

        var loginDTO = new LoginDTO(loginStr, passwordStr);

        given()
                .contentType(ContentType.JSON)
                .body(loginDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("should return an error when login fails due to wrong password")
    void shouldReturnErrorWhenPasswordIsWrong() {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        String loginStr = "login.fail." + unique;

        Long typeId = createTestUserType("ROLE_AUTH_FAIL_" + unique);
        createTestUser("Usuário Falha", "authfail_" + unique + "@email.com", loginStr, typeId, "senhaVerdadeira123");

        var loginDTO = new LoginDTO(loginStr, "senhaErrada456");

        given()
                .contentType(ContentType.JSON)
                .body(loginDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(500)
                .body("code", equalTo("INTERNAL_ERROR"));
    }
}