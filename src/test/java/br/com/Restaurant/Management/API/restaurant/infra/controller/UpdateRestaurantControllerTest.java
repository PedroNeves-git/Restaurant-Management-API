package br.com.Restaurant.Management.API.restaurant.infra.controller;

import br.com.Restaurant.Management.API.restaurant.core.dto.input.CreateRestaurantInputDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.input.UpdateRestaurantInputDTO;
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
class UpdateRestaurantControllerTest {

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
        String roleName = "REST_OWNER_UPDATE_" + uniqueCode;

        Long userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + roleName + "\"}")
                .when()
                .post("/userstype")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        var userRequest = new CreateUserInputDTO(
                "Dono Update",
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
    @DisplayName("should return 200 when restaurant is successfully updated")
    void shouldReturn200WhenRestaurantIsUpdated() {
        Long ownerId = createTestUser("owner_update", "owner.update");
        Long oldCuisineTypeId = createTestCuisineType("Francesa");
        Long newCuisineTypeId = createTestCuisineType("Contemporânea");

        String originalName = "Restaurante Velho " + UUID.randomUUID().toString().substring(0, 5);

        var createRequest = new CreateRestaurantInputDTO(
                originalName,
                "Rua Antiga, 100",
                "18:00",
                "23:00",
                oldCuisineTypeId,
                ownerId
        );

        Long restaurantId = given().contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/restaurant")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Precisamos extrair o ID para poder dar o PUT nele depois!

        String updatedName = "Restaurante Novo " + UUID.randomUUID().toString().substring(0, 5);
        var updateRequest = new UpdateRestaurantInputDTO(
                updatedName,
                "Rua Nova, 200",
                "19:00",
                "23:59",
                newCuisineTypeId
        );

        given().contentType(ContentType.JSON)
                .pathParam("id", restaurantId)
                .body(updateRequest)
                .when()
                .put("/restaurant/{id}")
                .then()
                .statusCode(200) // Assumindo que seu controller retorna 200 OK (se retornar 204 No Content, é só mudar aqui)
                .body("name", equalTo(updatedName))
                .body("address", equalTo("Rua Nova, 200"));
    }

    @Test
    @DisplayName("should return 404 when trying to update a non-existent restaurant")
    void shouldReturn404WhenUpdatingNonExistentRestaurant() {
        Long cuisineTypeId = createTestCuisineType("Fantasma");

        var updateRequest = new UpdateRestaurantInputDTO(
                "Restaurante Fantasma",
                "Rua do Além",
                "00:00",
                "01:00",
                cuisineTypeId
        );

        given().contentType(ContentType.JSON)
                .pathParam("id", 999999L)
                .body(updateRequest)
                .when()
                .put("/restaurant/{id}")
                .then()
                .statusCode(404);
    }
}