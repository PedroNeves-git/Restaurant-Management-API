package br.com.Restaurant.Management.API.users.core.dto.output;

import java.time.LocalDateTime;

public record UserOutputDTO(
        Long id,
        String name,
        String email,
        String login,
        boolean active,
        Long typeId,
        LocalDateTime createdAt
) {}
