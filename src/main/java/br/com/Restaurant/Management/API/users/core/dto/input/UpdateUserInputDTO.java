package br.com.Restaurant.Management.API.users.core.dto.input;

public record UpdateUserInputDTO(
        String name,
        String email,
        String login,
        Long typeId,
        boolean active
) {}