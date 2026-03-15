package br.com.Restaurant.Management.API.restaurant.infra.controller;

import br.com.Restaurant.Management.API.restaurant.core.dto.input.CreateRestaurantInputDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.usecase.CreateRestaurantUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurant")
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class CreateRestaurantController {
    private final CreateRestaurantUseCase createRestaurantUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantOutputDTO create(@RequestBody CreateRestaurantInputDTO request) {
        return createRestaurantUseCase.execute(request);
    }
}
