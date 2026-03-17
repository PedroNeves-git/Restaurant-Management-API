package br.com.Restaurant.Management.API.restaurant.infra.controller;

import br.com.Restaurant.Management.API.restaurant.core.dto.input.UpdateRestaurantInputDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.usecase.UpdateRestaurantUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurant")
@RestController
@RequestMapping("/restaurant")
public class UpdateRestaurantController {

    private final UpdateRestaurantUseCase updateRestaurantUseCase;

    public UpdateRestaurantController(UpdateRestaurantUseCase updateRestaurantUseCase) {
        this.updateRestaurantUseCase = updateRestaurantUseCase;
    }

    @PutMapping("/{id}")
    public RestaurantOutputDTO update(
            @PathVariable Long id,
            @RequestBody UpdateRestaurantInputDTO input
    ) {
        return updateRestaurantUseCase.execute(id, input);
    }
}