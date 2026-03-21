package br.com.Restaurant.Management.API.menuItems.core.dto.output;

import java.math.BigDecimal;

public record MenuItemOutputDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean availableOnlyInRestaurant,
        String imagePath,
        Long restaurantId
) {}