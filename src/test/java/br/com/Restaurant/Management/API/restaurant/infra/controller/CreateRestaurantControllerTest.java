package br.com.Restaurant.Management.API.restaurant.infra.controller;

import br.com.Restaurant.Management.API.restaurant.core.dto.input.CreateRestaurantInputDTO;
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

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class CreateRestaurantControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private Long createTestUser(String emailBase, String loginBase) {
        String uniqueCode = UUID.randomUUID().toString().substring(0, 8);
        String roleName = "REST_OWNER_" + uniqueCode;

        Long userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + roleName + "\"}")
                .when()
                .post("api/v1/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        var userRequest = new CreateUserInputDTO(
                "Dono Restaurante Teste",
                emailBase + "_" + uniqueCode + "@email.com",
                loginBase + "_" + uniqueCode,
                "senha123",
                userTypeId,
                UserRole.RESTAURANT_OWNER
        );

        return given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post("api/v1/users")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    private Long createTestCuisineType(String name) {
        String uniqueName = name + "_" + UUID.randomUUID().toString().substring(0, 8);
        return given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + uniqueName + "\"}")
                .when()
                .post("api/v1/cuisinetype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    @Test
    @DisplayName("should return 201 when restaurant is created successfully")
    void shouldReturnCreatedWhenRestaurantIsValid() {
        Long ownerId = createTestUser("owner_success", "owner.success");
        Long cuisineTypeId = createTestCuisineType("Japonesa");

        var request = new CreateRestaurantInputDTO(
                "Sakura House",
                "123 Main Street",
                "11:00",
                "23:00",
                cuisineTypeId,
                ownerId
        );

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("api/v1/restaurant")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Sakura House"));
    }

    @Test
    @DisplayName("should return 409 when restaurant name is already in use")
    void shouldReturnConflictWhenNameExists() {
        Long ownerId = createTestUser("owner_conflict", "owner.conflict");
        Long cuisineTypeId = createTestCuisineType("Italiana");

        var request = new CreateRestaurantInputDTO(
                "Pizzaria Napoli",
                "Rua das Pizzas 123",
                "18:00",
                "23:59",
                cuisineTypeId,
                ownerId
        );

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("api/v1/restaurant")
                .then()
                .statusCode(201);

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("api/v1/restaurant")
                .then()
                .statusCode(409)
                .body("code", equalTo("NAME_ALREADY_IN_USE"));
    }
}