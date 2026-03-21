package br.com.Restaurant.Management.API.usersType.core.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUserTypeInputDTO(
        @Schema(example = "Supplier")
        String name
) {
}
