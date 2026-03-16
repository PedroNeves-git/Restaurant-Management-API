package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListRestaurantsByOwnerIdUseCase {

    private final RestaurantGateway restaurantGateway;

    public List<RestaurantOutputDTO> execute(Long restaurantOwnerId) {
        List<Restaurant> restaurants = restaurantGateway.findRestaurantsByRestaurantOwnerId(restaurantOwnerId);

        if (restaurants.isEmpty()) {
            throw new RestaurantNotFoundException(restaurantOwnerId);
        }

        return restaurants.stream()
                .map(Restaurant::toOutput)
                .toList();
    }

}