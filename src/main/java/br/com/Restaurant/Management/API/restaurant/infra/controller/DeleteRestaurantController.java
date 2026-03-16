package br.com.Restaurant.Management.API.restaurant.infra.controller;

import br.com.Restaurant.Management.API.common.dto.BaseResponseDTO;
import br.com.Restaurant.Management.API.restaurant.core.usecase.DeleteRestaurantUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurant")
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class DeleteRestaurantController {

    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<BaseResponseDTO> delete(@PathVariable Long id) {
        deleteRestaurantUseCase.execute(id);

        return ResponseEntity.ok(
                BaseResponseDTO.success(
                        HttpStatus.OK.value(),
                        "RESTAURANT_DELETED",
                        "Restaurant deleted successfully"
                )
        );
    }
}