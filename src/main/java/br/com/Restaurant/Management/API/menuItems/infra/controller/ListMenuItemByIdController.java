package br.com.Restaurant.Management.API.menuItems.infra.controller;

import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.usecase.ListMenuItemByIdUseCase;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.usecase.ListUserTypeByIdUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Menu Items")
@RestController
@RequestMapping("/menu-items")
public class ListMenuItemByIdController {
    private final ListMenuItemByIdUseCase useCase;
    public ListMenuItemByIdController(ListMenuItemByIdUseCase useCase) { this.useCase = useCase; }

    @GetMapping("/{id}")
    public MenuItemOutputDTO listById(@PathVariable Long id) {
        return useCase.execute(id);
    }
}
