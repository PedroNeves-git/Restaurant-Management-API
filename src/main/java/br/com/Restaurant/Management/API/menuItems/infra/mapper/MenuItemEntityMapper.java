package br.com.Restaurant.Management.API.menuItems.infra.mapper;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.MenuItemPersistenceDTO;
import br.com.Restaurant.Management.API.menuItems.infra.entity.MenuItemEntity;

public class MenuItemEntityMapper {

    public static MenuItemEntity toEntity(MenuItem menuItem) {
        MenuItemPersistenceDTO dto = menuItem.export();
        return new MenuItemEntity(dto.id(), dto.name(), dto.description(), dto.price(),
                dto.availableOnlyInRestaurant(), dto.imagePath(), dto.restaurantId());
    }

    public static MenuItem toDomain(MenuItemEntity entity) {
        return MenuItem.restore(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getPrice(), entity.getAvailableOnlyInRestaurant(),
                entity.getImagePath(), entity.getRestaurantId());
    }
}