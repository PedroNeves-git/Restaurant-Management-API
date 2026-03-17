package br.com.Restaurant.Management.API.restaurant.infra.mapper;

import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.infra.entity.RestaurantEntity;

public class RestaurantEntityMapper {

    public static RestaurantEntity toEntity(Restaurant restaurant) {

        var data = restaurant.export();

        return new RestaurantEntity(
                data.id(),
                data.name(),
                data.address(),
                data.openingTime(),
                data.closingTime(),
                data.cuisineTypeId(),
                data.restaurantOwnerId()
        );
    }

    public static Restaurant toDomain(RestaurantEntity data) {
        return Restaurant.restore(
                data.getId(),
                data.getName(),
                data.getAddress(),
                data.getOpeningTime().toString(),
                data.getClosingTime().toString(),
                data.getCuisineTypeId(),
                data.getRestaurantOwnerId()
        );
    }
}
