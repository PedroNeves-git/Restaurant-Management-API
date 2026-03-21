package br.com.Restaurant.Management.API.menuItems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.exception.MenuItemNotFoundException;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;

public class DeleteMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;

    public DeleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(Long id) {
        menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
        menuItemGateway.deleteById(id);
    }
}