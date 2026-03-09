package br.com.Restaurant.Management.API.users.core.dto.input;

public record CreateUserInputDTO(
        String name,
        String email,
        String login,
        String password,
        Long type_id
) {}
