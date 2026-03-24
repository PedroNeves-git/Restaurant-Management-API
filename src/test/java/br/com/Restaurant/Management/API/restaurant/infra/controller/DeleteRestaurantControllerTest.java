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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class DeleteRestaurantControllerTest {

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
        String roleName = "REST_OWNER_DEL_" + uniqueCode;

        Long userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + roleName + "\"}")
                .when()
                .post("api/v1/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        var userRequest = new CreateUserInputDTO(
                "Dono Delete",
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
    @DisplayName("should return 200 when restaurant is deleted successfully")
    void shouldDeleteRestaurantSuccessfully() {

        Long ownerId = createTestUser("owner_delete", "owner.delete");
        Long cuisineTypeId = createTestCuisineType("Churrascaria");

        String restaurantName = "Restaurante Deletado " + UUID.randomUUID().toString().substring(0, 5);

        var createRequest = new CreateRestaurantInputDTO(
                restaurantName,
                "Avenida da Fumaça, 500",
                "11:00",
                "15:00",
                cuisineTypeId,
                ownerId
        );

        Long restaurantId = given().contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("api/v1/restaurant")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given().pathParam("id", restaurantId)
                .when()
                .delete("api/v1/restaurant/{id}")
                .then()
                .statusCode(200) // O controller retorna ResponseEntity.ok()
                .body("code", equalTo("RESTAURANT_DELETED"))
                .body("message", equalTo("Restaurant deleted successfully"));
    }

    @Test
    @DisplayName("should return 404 when trying to delete a non-existent restaurant")
    void shouldReturn404WhenNotFound() {

        given().pathParam("id", 999999L)
                .when()
                .delete("api/v1/restaurant/{id}")
                .then()
                .statusCode(404);
    }
}