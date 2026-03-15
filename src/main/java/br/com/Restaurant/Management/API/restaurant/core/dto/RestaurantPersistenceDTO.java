package br.com.Restaurant.Management.API.restaurant.core.dto;

import java.time.LocalTime;

public record RestaurantPersistenceDTO(
        Long id,
        String name,
        String address,
        LocalTime openingTime,
        LocalTime closingTime,
        Long cuisineTypeId,
        Long restaurantOwnerId
) {
}
