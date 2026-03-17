package br.com.Restaurant.Management.API.users.core.dto;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;

import java.time.LocalDateTime;

public record UserPersistenceDTO(
        Long id,
        String name,
        String email,
        String login,
        String password, // depois: passwordHash
        boolean active,
        Long typeId,
        UserRole userRole,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
