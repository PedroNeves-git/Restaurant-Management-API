package br.com.Restaurant.Management.API.restaurant.core.dto.output;

import java.time.LocalTime;

public record RestaurantOutputDTO(
        Long id,
        String name,
        String address,
        LocalTime openingTime,
        LocalTime closingTime,
        Long cuisineTypeId,
        Long restaurantOwnerId
) {}
