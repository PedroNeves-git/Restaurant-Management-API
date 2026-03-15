package br.com.Restaurant.Management.API.restaurant.core.dto.input;

public record CreateRestaurantInputDTO(
        String name,
        String address,
        String openingTime,
        String closingTime,
        Long cuisine_type_id,
        Long restaurant_owner_id
) {}
