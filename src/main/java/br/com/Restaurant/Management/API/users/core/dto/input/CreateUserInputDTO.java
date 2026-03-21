package br.com.Restaurant.Management.API.users.core.dto.input;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUserInputDTO(
        @Schema(example = "John Doe") String name,
        @Schema(example = "john@email.com") String email,
        @Schema(example = "johndoe") String login,
        @Schema(example = "Password@123") String password,
        @Schema(example = "1") Long type_id,
        @Schema(example = "RESTAURANT_OWNER") UserRole role
) {}
