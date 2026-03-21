package br.com.Restaurant.Management.API.menuItems.infra.controller;

import br.com.Restaurant.Management.API.menuItems.core.usecase.DeleteMenuItemUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu Items")
@RestController
@RequestMapping("/menu-items")
public class DeleteMenuItemController {
    private final DeleteMenuItemUseCase useCase;
    public DeleteMenuItemController(DeleteMenuItemUseCase useCase) { this.useCase = useCase; }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        useCase.execute(id);
    }
}
