package br.com.Restaurant.Management.API.restaurant.core.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateRestaurantInputDTO(
        @Schema(example = "Sakura House") String name,
        @Schema(example = "123 Main Street") String address,
        @Schema(example = "11:00") String openingTime,
        @Schema(example = "23:00") String closingTime,
        @Schema(example = "2") Long cuisineTypeId
) {}
