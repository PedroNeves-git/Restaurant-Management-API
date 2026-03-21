package br.com.Restaurant.Management.API.menuItems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.dto.input.UpdateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.exception.MenuItemNotFoundException;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;

public class UpdateMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;

    public UpdateMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItemOutputDTO execute(Long id, UpdateMenuItemInputDTO input) {
        var menuItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));

        menuItem.update(input.name(), input.description(), input.price(),
                input.availableOnlyInRestaurant(), input.imagePath());

        return menuItemGateway.update(menuItem).toOutput();
    }
}