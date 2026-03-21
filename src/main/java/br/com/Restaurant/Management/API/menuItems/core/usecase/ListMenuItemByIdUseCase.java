package br.com.Restaurant.Management.API.menuItems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.exception.MenuItemNotFoundException;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;

public class ListMenuItemByIdUseCase {
    private final MenuItemGateway menuItemGateway;

    public ListMenuItemByIdUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItemOutputDTO execute(Long id) {
        return menuItemGateway.findById(id)
                .map(MenuItem -> MenuItem.toOutput())
                .orElseThrow(() -> new MenuItemNotFoundException(id));
    }
}