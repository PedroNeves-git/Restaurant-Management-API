package br.com.Restaurant.Management.API.cuisinetype.core.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCuisineTypeInputDTO(
        @Schema(example = "Chinese")
        String name
) {
}
