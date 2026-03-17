package br.com.Restaurant.Management.API.restaurant.infra.config;

import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import br.com.Restaurant.Management.API.restaurant.core.usecase.*;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantBeanConfig {

    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(
            RestaurantGateway  restaurantGateway,
            UserGateway userGateway,
            CuisineTypeGateway cuisineTypeGateway
    ) {
        return new CreateRestaurantUseCase(restaurantGateway, userGateway, cuisineTypeGateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUseCase(
            RestaurantGateway restaurantGateway
    ) {
        return new DeleteRestaurantUseCase(restaurantGateway);
    }

    @Bean
    public ListRestaurantsByOwnerIdUseCase listRestaurantsByOwnerIdUseCase(RestaurantGateway restaurantGateway) {
        return new ListRestaurantsByOwnerIdUseCase(restaurantGateway);
    }

    @Bean
    public ListRestaurantsUseCase listRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        return new ListRestaurantsUseCase(restaurantGateway);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUseCase(RestaurantGateway restaurantGateway, CuisineTypeGateway cuisineTypeGateway) {
        return new UpdateRestaurantUseCase(restaurantGateway, cuisineTypeGateway);
    }
}
