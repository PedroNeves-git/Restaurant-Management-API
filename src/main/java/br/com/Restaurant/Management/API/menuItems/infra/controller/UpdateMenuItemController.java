package br.com.Restaurant.Management.API.menuItems.infra.controller;

import br.com.Restaurant.Management.API.menuItems.core.dto.input.UpdateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.usecase.UpdateMenuItemUseCase;
import br.com.Restaurant.Management.API.usersType.core.dto.input.UpdateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.usecase.UpdateUserTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu Items")
@RestController
@RequestMapping("/menu-items")
public class UpdateMenuItemController {
    private final UpdateMenuItemUseCase useCase;
    public UpdateMenuItemController(UpdateMenuItemUseCase useCase) { this.useCase = useCase; }

    @PutMapping("/{id}")
    public MenuItemOutputDTO update(@PathVariable Long id, @RequestBody UpdateMenuItemInputDTO input) {
        return useCase.execute(id, input);
    }
}
