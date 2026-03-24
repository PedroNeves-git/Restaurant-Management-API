package br.com.Restaurant.Management.API.menuitems.infra.controller;

import br.com.Restaurant.Management.API.cuisinetype.infra.repository.CuisineTypeJpaRepository;
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

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListMenuItemByIdControllerTest {
    @LocalServerPort
    private int port;

    @Autowired private MenuItemJpaRepository menuItemRepository;
    @Autowired private RestaurantJpaRepository restaurantRepository;
    @Autowired private UserJpaRepository userRepository;
    @Autowired private UserTypeJpaRepository userTypeRepository;
    @Autowired private CuisineTypeJpaRepository cuisineTypeRepository;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        menuItemRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
        cuisineTypeRepository.deleteAll();
    }

    @Test
    @DisplayName("should return 200 when menu item exists")
    void shouldReturn200WhenItemExists() {
        int menuItemId = createScenario();

        given().pathParam("id", menuItemId)
                .contentType(ContentType.JSON)
                .when()
                .get("/menu-items/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(menuItemId))
                .body("name", equalTo("Pizza Test"));
    }

    @Test
    @DisplayName("should return 404 when menu item does not exist")
    void shouldReturn404WhenItemNotFound() {
        given().pathParam("id", 9999)
                .contentType(ContentType.JSON)
                .when()
                .get("/menu-items/{id}")
                .then()
                .statusCode(404)
                .body("code", equalTo("MENU_ITEM_NOT_FOUND"));
    }

    private int createScenario() {
        int utId = given().contentType(ContentType.JSON)
                .body(Map.of("name", "OWNER_BY_ID"))
                .post("/userstype")
                .then().statusCode(201).extract().jsonPath().getInt("id");

        int uId = given().contentType(ContentType.JSON).body(Map.of(
                "name", "Owner", "login", "ownerbyid", "email", "byid@test.com",
                "password", "123", "role", "RESTAURANT_OWNER", "type_id", utId
        )).post("/users").then().statusCode(201).extract().jsonPath().getInt("id");

        int cId = given().contentType(ContentType.JSON)
                .body(Map.of("name", "Italian"))
                .post("/cuisinetype")
                .then().statusCode(201).extract().jsonPath().getInt("id");

        int rId = given().contentType(ContentType.JSON).body(Map.of(
                "name", "Rest By Id",
                "address", "Rua Teste, 123",
                "openingTime", "11:00",
                "closingTime", "23:00",
                "cuisine_type_id", cId,
                "restaurant_owner_id", uId
        )).post("/restaurant").then().statusCode(201).extract().jsonPath().getInt("id");

        return given().contentType(ContentType.JSON).body(Map.of(
                "name", "Pizza Test", "description", "Desc", "price", 40.0, "restaurantId", rId
        )).post("/menu-items").then().statusCode(201).extract().jsonPath().getInt("id");
    }
}