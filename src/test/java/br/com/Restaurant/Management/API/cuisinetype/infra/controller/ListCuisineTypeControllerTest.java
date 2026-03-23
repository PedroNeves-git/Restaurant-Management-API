package br.com.Restaurant.Management.API.cuisinetype.infra.controller;

import br.com.Restaurant.Management.API.cuisinetype.core.dto.input.CreateCuisineTypeInputDTO;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class ListCuisineTypeControllerTeste {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/cuisinetype";
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