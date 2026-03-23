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
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListUsersControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return paginated users successfully")
    void shouldReturnPaginatedUsers() {
        int userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"LIST_USERS_ROLE\"}")
                .post("/userstype")
                .then().extract().jsonPath().getInt("id");

        var userRequest = new CreateUserInputDTO(
                "Usuario Lista",
                "lista@email.com",
                "login.lista",
                "123456",
                Long.valueOf(userTypeId),
                UserRole.RESTAURANT_OWNER
        );

        given().contentType(ContentType.JSON)
                .body(userRequest)
                .post("/users");

        given().queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThanOrEqualTo(1)))
                .body("page", is(0))
                .body("size", is(10))
                .body("totalElements", is(notNullValue()))
                .body("content.name", hasItem("Usuario Lista"));
    }
}