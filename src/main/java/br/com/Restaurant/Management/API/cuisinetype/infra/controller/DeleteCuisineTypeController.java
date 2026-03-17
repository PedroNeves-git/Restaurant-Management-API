package br.com.Restaurant.Management.API.cuisinetype.infra.controller;

import br.com.Restaurant.Management.API.common.dto.BaseResponseDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.DeleteCuisineTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cuisine Types")
@RestController
@RequestMapping("/cuisinetype")
public class DeleteCuisineTypeController {

    private final DeleteCuisineTypeUseCase deleteCuisineTypeUseCase;

    public DeleteCuisineTypeController(DeleteCuisineTypeUseCase deleteCuisineTypeUseCase) {
        this.deleteCuisineTypeUseCase = deleteCuisineTypeUseCase;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<BaseResponseDTO> delete(@PathVariable Long id) {
        deleteCuisineTypeUseCase.execute(id);

        return ResponseEntity.ok(
                BaseResponseDTO.success(
                        HttpStatus.OK.value(),
                        "CUISINETYPE_DELETED",
                        "Cuisine Type deleted successfully"
                )
        );
    }
}
