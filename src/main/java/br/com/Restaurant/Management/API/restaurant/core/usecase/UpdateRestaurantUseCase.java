package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.input.UpdateRestaurantInputDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.exception.NameAlreadyInUseException;
import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final CuisineTypeGateway cuisineTypeGateway;

    public RestaurantOutputDTO execute(Long id, UpdateRestaurantInputDTO input) {
        Restaurant restaurant = restaurantGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        if (!restaurant.export().name().equals(input.name()) &&
                restaurantGateway.existsByName(input.name())) {
            throw new NameAlreadyInUseException(input.name());
        }

        cuisineTypeGateway.findById(input.cuisineTypeId())
                .orElseThrow(() -> new CuisineTypeNotFoundException(input.cuisineTypeId()));

        restaurant.update(
                input.name(),
                input.address(),
                input.openingTime(),
                input.closingTime(),
                input.cuisineTypeId()
        );

        Restaurant updated = restaurantGateway.update(restaurant);
        return updated.toOutput();
    }
}