package br.com.Restaurant.Management.API.cuisinetype.infra.controller;

import br.com.Restaurant.Management.API.cuisinetype.core.dto.input.CreateCuisineTypeInputDTO;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListCuisineTypeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MenuItemJpaRepository menuItemRepository;
    @Autowired private RestaurantJpaRepository restaurantRepository;
    @Autowired private CuisineTypeJpaRepository cuisineTypeRepository;
    @Autowired private UserJpaRepository userRepository;
    @Autowired private UserTypeJpaRepository userTypeRepository;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "api/v1/cuisinetype";

    }

    @Test
    @DisplayName("should return 200 and paginated list of cuisine types")
    void shouldReturnPaginatedCuisineTypes() {
        given().contentType(ContentType.JSON)
                .body(new CreateCuisineTypeInputDTO("Italiana"))
                .post();

        given().contentType(ContentType.JSON)
                .body(new CreateCuisineTypeInputDTO("Chinesa"))
                .post();

        given().queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThanOrEqualTo(2)))
                .body("content.name", hasItems("Italiana", "Chinesa"))
                .body("totalElements", notNullValue())
                .body("page", is(0))
                .body("size", is(10));
    }
}