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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListUsersTypeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return paginated user types successfully")
    void shouldReturnPaginatedUserTypes() {
        given().contentType(ContentType.JSON)
                .body(new CreateUserTypeInputDTO("ADMIN_LIST"))
                .post("/userstype");

        given().contentType(ContentType.JSON)
                .body(new CreateUserTypeInputDTO("CLIENT_LIST"))
                .post("/userstype");

        given().queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/userstype")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThanOrEqualTo(2)))
                .body("totalElements", is(notNullValue()))
                .body("page", is(0))
                .body("size", is(10))
                .body("content.name", anyOf(hasItem("ADMIN_LIST"), hasItem("CLIENT_LIST")));
    }
}