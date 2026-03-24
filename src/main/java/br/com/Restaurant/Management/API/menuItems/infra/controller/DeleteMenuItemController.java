package br.com.Restaurant.Management.API.menuItems.infra.controller;

import br.com.Restaurant.Management.API.common.dto.BaseResponseDTO;
import br.com.Restaurant.Management.API.menuItems.core.usecase.DeleteMenuItemUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu Items")
@RestController
@RequestMapping("/menu-items")
public class DeleteMenuItemController {
    private final DeleteMenuItemUseCase useCase;
    public DeleteMenuItemController(DeleteMenuItemUseCase useCase) { this.useCase = useCase; }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO> delete(@PathVariable Long id) {
        useCase.execute(id);

        return ResponseEntity.ok(
                BaseResponseDTO.success(
                        HttpStatus.OK.value(),
                        "ITEM_DELETED",
                        "Menu item deleted successfully"
                )
        );
    }
}
