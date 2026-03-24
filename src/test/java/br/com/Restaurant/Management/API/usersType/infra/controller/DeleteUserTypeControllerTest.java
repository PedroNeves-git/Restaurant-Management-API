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
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class DeleteUserTypeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 when user type is deleted successfully")
    void shouldDeleteUserTypeSuccessfully() {
        var request = new CreateUserTypeInputDTO("TEMPORARIO");
        int idParaDeletar = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("api/v1/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        given().pathParam("id", idParaDeletar)
                .when()
                .delete("api/v1/userstype/{id}")
                .then()
                .statusCode(200)
                .body("code", equalTo("USERTYPE_DELETED"))
                .body("message", equalTo("User Type deleted successfully"));
    }

    @Test
    @DisplayName("should return 404 when user type to delete does not exist")
    void shouldReturn404WhenNotFound() {
        given().pathParam("id", 9999)
                .when()
                .delete("api/v1/userstype/{id}")
                .then()
                .statusCode(404)
                .body("code", equalTo("USER_TYPE_NOT_FOUND"));
    }
}
