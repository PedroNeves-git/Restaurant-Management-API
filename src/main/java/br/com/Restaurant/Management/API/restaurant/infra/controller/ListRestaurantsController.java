package br.com.Restaurant.Management.API.restaurant.infra.controller;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.usecase.ListRestaurantsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Restaurants")
@RestController
@RequestMapping("/restaurant")
public class ListRestaurantsController {

    private final ListRestaurantsUseCase listRestaurantsUseCase;

    public ListRestaurantsController(ListRestaurantsUseCase listRestaurantsUseCase) {
        this.listRestaurantsUseCase = listRestaurantsUseCase;
    }

    @GetMapping
    public PaginatedResponseDTO<RestaurantOutputDTO> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return listRestaurantsUseCase.execute(page, size);
    }
}
