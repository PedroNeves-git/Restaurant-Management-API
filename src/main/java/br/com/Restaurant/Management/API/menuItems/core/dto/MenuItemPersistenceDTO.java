package br.com.Restaurant.Management.API.menuItems.core.dto;

import java.math.BigDecimal;

public record MenuItemPersistenceDTO(Long id,
                                     String name,
                                     String description,
                                     BigDecimal price,
                                     Boolean availableOnlyInRestaurant,
                                     String imagePath,
                                     Long restaurantId
) { }
