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
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListRestaurantsByOwnerIdControllerTest {

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
        String roleName = "REST_OWNER_BY_ID_" + uniqueCode;

        Long userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + roleName + "\"}")
                .when()
                .post("/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        var userRequest = new CreateUserInputDTO(
                "Dono Busca Por ID",
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
                .post("/users")
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
                .post("/cuisinetype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    @Test
    @DisplayName("should return 200 and the list of restaurants when owner has restaurants")
    void shouldReturnRestaurantsWhenOwnerIdExists() {
        Long ownerId = createTestUser("owner_find_id", "owner.find.id");
        Long cuisineTypeId = createTestCuisineType("Mexicana");

        String restaurantName = "Restaurante Mexicano " + UUID.randomUUID().toString().substring(0, 5);

        var request = new CreateRestaurantInputDTO(
                restaurantName,
                "Rua dos Tacos, 123",
                "18:00",
                "23:00",
                cuisineTypeId,
                ownerId
        );

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/restaurant")
                .then()
                .statusCode(201);

        given().pathParam("id", ownerId)
                .when()
                .get("/restaurant/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("name", hasItem(restaurantName));
    }

    @Test
    @DisplayName("should return 404 when owner has no restaurants or owner does not exist")
    void shouldReturn404WhenOwnerHasNoRestaurants() {
        given().pathParam("id", 999999L)
                .when()
                .get("/restaurant/{id}")
                .then()
                .statusCode(404);
    }
}