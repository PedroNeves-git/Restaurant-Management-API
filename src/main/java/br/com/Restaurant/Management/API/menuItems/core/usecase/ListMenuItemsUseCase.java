package br.com.Restaurant.Management.API.menuItems.core.usecase;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;

import java.util.List;

public class ListMenuItemsUseCase {
    private final MenuItemGateway menuItemGateway;

    public ListMenuItemsUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public PaginatedResponseDTO<MenuItemOutputDTO> execute(int page, int size) {
        PaginatedResponseDTO<MenuItem> items = menuItemGateway.findAll(page, size);

        List<MenuItemOutputDTO> content = items.content().stream()
                .map(MenuItem::toOutput)
                .toList();

        return new PaginatedResponseDTO<>(content, items.page(), items.size(),
                items.totalElements(), items.totalPages());
    }
}