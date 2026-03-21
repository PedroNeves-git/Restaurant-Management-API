package br.com.Restaurant.Management.API.menuItems.infra.controller;

import br.com.Restaurant.Management.API.menuItems.core.dto.input.CreateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.usecase.CreateMenuItemUseCase;
import br.com.Restaurant.Management.API.usersType.core.dto.input.CreateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.usecase.CreateUserTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu Items")
@RestController
@RequestMapping("/menu-items")
public class CreateMenuItemController {
    private final CreateMenuItemUseCase useCase;
    public CreateMenuItemController(CreateMenuItemUseCase useCase) { this.useCase = useCase; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemOutputDTO create(@RequestBody CreateMenuItemInputDTO input) {
        return useCase.execute(input);
    }
}
