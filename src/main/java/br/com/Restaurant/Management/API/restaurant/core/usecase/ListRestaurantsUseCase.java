package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListRestaurantsUseCase {

    private final RestaurantGateway restaurantGateway;

    public PaginatedResponseDTO<RestaurantOutputDTO> execute(int page, int size) {
        PaginatedResponseDTO<Restaurant> restaurants = restaurantGateway.findAll(page, size);

        List<RestaurantOutputDTO> content = restaurants.content().stream()
                .map(Restaurant::toOutput)
                .toList();

        return new PaginatedResponseDTO<>(
                content,
                restaurants.page(),
                restaurants.size(),
                restaurants.totalElements(),
                restaurants.totalPages()
        );
    }
}
