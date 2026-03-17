package br.com.Restaurant.Management.API.users.core.dto.input;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;

public record CreateUserInputDTO(
        String name,
        String email,
        String login,
        String password,
        Long type_id,
        UserRole role
) {}
