package br.com.Restaurant.Management.API.menuItems.infra.controller;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.usecase.ListMenuItemsUseCase;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.usecase.ListUsersTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Menu Items")
@RestController
@RequestMapping("/menu-items")
public class ListMenuItemsController {
    private final ListMenuItemsUseCase useCase;
    public ListMenuItemsController(ListMenuItemsUseCase useCase) { this.useCase = useCase; }

    @GetMapping
    public PaginatedResponseDTO<MenuItemOutputDTO> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return useCase.execute(page, size);
    }
}
