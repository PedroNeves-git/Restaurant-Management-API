package br.com.Restaurant.Management.API.menuItems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.input.CreateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;

public class CreateMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuItemOutputDTO execute(CreateMenuItemInputDTO input) {
        restaurantGateway.findById(input.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(input.restaurantId()));

        MenuItem menuItem = MenuItem.newMenuItem(
                input.name(),
                input.description(),
                input.price(),
                input.availableOnlyInRestaurant(),
                input.imagePath(),
                input.restaurantId()
        );
        return menuItemGateway.create(menuItem).toOutput();
    }
}