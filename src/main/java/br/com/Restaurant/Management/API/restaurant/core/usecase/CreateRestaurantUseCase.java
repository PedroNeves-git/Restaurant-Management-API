package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.input.CreateRestaurantInputDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.exception.NameAlreadyInUseException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;
    private final CuisineTypeGateway cuisineTypeGateway;

    public RestaurantOutputDTO execute(CreateRestaurantInputDTO input) {

        userGateway.findById(input.restaurant_owner_id())
                .orElseThrow(() -> new UserNotFoundException(input.restaurant_owner_id()));

        if (restaurantGateway.existsByName(input.name())) {
            throw new NameAlreadyInUseException(input.name());
        }

        cuisineTypeGateway.findById(input.cuisine_type_id())
                .orElseThrow(() -> new CuisineTypeNotFoundException(input.cuisine_type_id()));

        Restaurant restaurant = Restaurant.newRestaurant(
                input.name(),
                input.address(),
                input.openingTime(),
                input.closingTime(),
                input.cuisine_type_id(),
                input.restaurant_owner_id()
        );

        Restaurant createdRestaurant = restaurantGateway.create(restaurant);
        return createdRestaurant.toOutput();
    }
}
