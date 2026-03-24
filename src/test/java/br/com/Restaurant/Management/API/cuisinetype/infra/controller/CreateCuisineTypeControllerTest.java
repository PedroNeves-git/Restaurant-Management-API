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

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfigurationsTest.class)
class CreateCuisineTypeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("should return 201 when cuisine type is created successfully")
    void shouldReturnCreatedWhenCuisineTypeIsValid() {
        String uniqueCuisineName = "Cuisine_" + UUID.randomUUID().toString().substring(0, 8);
        var request = new CreateCuisineTypeInputDTO(uniqueCuisineName);

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/cuisinetype")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", equalTo(uniqueCuisineName))
                .body("id", notNullValue());
    }

}