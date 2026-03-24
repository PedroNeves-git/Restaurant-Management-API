package br.com.Restaurant.Management.API.menuitems.infra.controller;

import br.com.Restaurant.Management.API.cuisinetype.infra.repository.CuisineTypeJpaRepository;
import br.com.Restaurant.Management.API.menuItems.core.dto.input.UpdateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.infra.repository.MenuItemJpaRepository;
import br.com.Restaurant.Management.API.restaurant.infra.repository.RestaurantJpaRepository;
import br.com.Restaurant.Management.API.users.infra.gateway.config.SecurityConfigurationsTest;
import br.com.Restaurant.Management.API.users.infra.repository.UserJpaRepository;
import br.com.Restaurant.Management.API.usersType.infra.repository.UserTypeJpaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class UpdateMenuItemControllerTest {
    @Autowired
    private MenuItemJpaRepository menuItemRepository;
    @Autowired
    private RestaurantJpaRepository restaurantRepository;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private UserTypeJpaRepository userTypeRepository;
    @Autowired
    private CuisineTypeJpaRepository cuisineTypeRepository;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        menuItemRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
        cuisineTypeRepository.deleteAll();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 when menu item is updated successfully")
        void shouldUpdateMenuItemSuccessfully() {
        var uniqueSuffix = String.valueOf(System.currentTimeMillis());
        var roleName = "OWNER_" + uniqueSuffix;

        int userTypeId = given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", roleName))
                .post("/userstype")
                .then().statusCode(201).extract().jsonPath().getInt("id");

        int userId = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", "Owner Test",
                        "login", "ownertest",
                        "email", "owner@test.com",
                        "password", "123456",
                        "role", "RESTAURANT_OWNER",
                        "type_id", userTypeId
                ))
                .post("/users")
                .then().statusCode(201).extract().jsonPath().getInt("id");

        int cuisineId = given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Italian"))
                .post("/cuisinetype")
                .then().statusCode(201).extract().jsonPath().getInt("id");

        int restaurantId = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", "Test Restaurant",
                        "address", "Rua de Teste, 123",
                        "openingTime", "11:00",
                        "closingTime", "23:00",
                        "cuisine_type_id", cuisineId,
                        "restaurant_owner_id", userId
                ))
                .post("/restaurant")
                .then().statusCode(201).extract().jsonPath().getInt("id");

        var createMenuRequest = Map.of(
                "name", "Original Burger",
                "description", "Old description",
                "price", 30.0,
                "availableOnlyInRestaurant", false,
                "imagePath", "old.png",
                "restaurantId", restaurantId
        );

        int menuItemId = given()
                .contentType(ContentType.JSON)
                .body(createMenuRequest)
                .post("/menu-items")
                .then().statusCode(201).extract().jsonPath().getInt("id");

        var updateRequest = new UpdateMenuItemInputDTO(
                "New Burger",
                "Updated description",
                BigDecimal.valueOf(35.50),
                true,
                "new.png"
        );

        given().pathParam("id", menuItemId)
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/menu-items/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(menuItemId))
                .body("name", equalTo("New Burger"))
                .body("price", equalTo(35.5f));
    }

}