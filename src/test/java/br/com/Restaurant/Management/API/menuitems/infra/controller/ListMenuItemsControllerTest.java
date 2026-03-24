package br.com.Restaurant.Management.API.menuitems.infra.controller;

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

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListMenuItemsControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 200 and paginated list of menu items")
    void shouldReturnPaginatedMenuItems() {
        int menuItemId = createFullScenario();
        given().queryParam("page", 0)
                .queryParam("size", 10)
                .contentType(ContentType.JSON)
                .when()
                .get("/menu-items")
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThanOrEqualTo(1)))
                .body("content[0].id", notNullValue())
                .body("page", equalTo(0))
                .body("size", equalTo(10))
                .body("totalElements", is(notNullValue()));
    }



    private int createFullScenario() {
        int utId = given().contentType(ContentType.JSON)
                .body(Map.of("name", "OWNER_LIST"))
                .post("/userstype")
                .then()
                .extract()
                .jsonPath().getInt("id");

        int uId = given().contentType(ContentType.JSON)
                .body(Map.of(
                "name", "List Owner", "login", "listowner", "email", "list@test.com",
                "password", "123", "role", "RESTAURANT_OWNER", "type_id", utId))
                .post("/users")
                .then()
                .extract()
                .jsonPath().getInt("id");

        int cId = given().contentType(ContentType.JSON)
                .body(Map.of("name", "Japanese"))
                .post("/cuisinetype").then().extract()
                .jsonPath().getInt("id");

        int rId = given().contentType(ContentType.JSON)
                .body(Map.of(
                "name", "List Rest", "address", "Rua Listagem, 100", "openingTime", "11:00",
                "closingTime", "23:00", "cuisine_type_id", cId, "restaurant_owner_id", uId))
                .post("/restaurant")
                .then()
                .extract()
                .jsonPath().getInt("id");

        return given().contentType(ContentType.JSON).body(Map.of(
                "name", "Sushi Combo", "description", "Fresh fish", "price", 80.0, "restaurantId", rId
        )).post("/menu-items").then().extract().jsonPath().getInt("id");
    }
}