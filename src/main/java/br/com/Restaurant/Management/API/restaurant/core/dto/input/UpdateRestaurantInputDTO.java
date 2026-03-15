package br.com.Restaurant.Management.API.restaurant.core.dto.input;

public record UpdateRestaurantInputDTO(
        String name,
        String address,
        String openingTime,
        String closingTime,
        Long cuisineTypeId
) {}
