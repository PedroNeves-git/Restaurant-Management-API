package br.com.Restaurant.Management.API.users.core.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserInputDTO(
        @Schema(example = "John Doe") String name,
        @Schema(example = "john@email.com") String email,
        @Schema(example = "johndoe") String login,
        @Schema(example = "1") Long typeId,
        @Schema(example = "true") boolean active
) {}