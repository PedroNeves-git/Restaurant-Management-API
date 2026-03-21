package br.com.Restaurant.Management.API.menuItems.core.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CreateMenuItemInputDTO(
        @Schema(example = "Salmon Sushi") String name,
        @Schema(example = "Fresh salmon over seasoned rice") String description,
        @Schema(example = "18.90") BigDecimal price,
        @Schema(example = "false") Boolean availableOnlyInRestaurant,
        @Schema(example = "/images/salmon-sushi.jpg") String imagePath,
        @Schema(example = "1") Long restaurantId
) {}