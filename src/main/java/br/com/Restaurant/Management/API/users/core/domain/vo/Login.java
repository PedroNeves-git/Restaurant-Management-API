package br.com.Restaurant.Management.API.users.core.domain.vo;

import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;

public record Login(String value) {
    public Login(String value) {
        if (value == null) {
            throw new InvalidFieldException("login", "REQUIRED", "Login is required");
        }

        String normalized = value.trim().toLowerCase();

        if (normalized.isBlank()) {
            throw new InvalidFieldException("login", "REQUIRED", "Login is required");
        }

        if (normalized.length() < 3 || normalized.length() > 30) {
            throw new InvalidFieldException("login", "INVALID_LENGTH", "Login must be between 3 and 30 characters");
        }

        if (!normalized.matches("^[a-z0-9._-]+$")) {
            throw new InvalidFieldException("login", "INVALID_FORMAT", "Login contains invalid characters");
        }

        this.value = normalized;
    }
}