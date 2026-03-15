package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public void execute(Long id) {
        restaurantGateway.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        restaurantGateway.deleteById(id);
    }
}
