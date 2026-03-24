package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.menuItems.infra.repository.MenuItemJpaRepository;
import br.com.Restaurant.Management.API.restaurant.infra.repository.RestaurantJpaRepository;
import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
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
class ListUsersControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private UserTypeJpaRepository userTypeRepository;
    @Autowired
    private RestaurantJpaRepository restaurantRepository;
    @Autowired
    private MenuItemJpaRepository menuItemRepository;
    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";

        menuItemRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        userTypeRepository.deleteAll();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    @DisplayName("should return paginated users successfully")
    void shouldReturnPaginatedUsers() {
        int userTypeId = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"LIST_USERS_ROLE\"}")
                .post("api/v1/userstype")
                .then().extract().jsonPath().getInt("id");

        var userRequest = new CreateUserInputDTO(
                "Usuario Lista",
                "lista@email.com",
                "login.lista",
                "123456",
                Long.valueOf(userTypeId),
                UserRole.RESTAURANT_OWNER
        );

        given().contentType(ContentType.JSON)
                .body(userRequest)
                .post("api/v1/users");

        given().queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("api/v1/users")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThanOrEqualTo(1)))
                .body("page", is(0))
                .body("size", is(10))
                .body("totalElements", is(notNullValue()))
                .body("content.name", hasItem("Usuario Lista"));
    }
}