package br.com.Restaurant.Management.API.restaurant.core.domain.vo;

import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;

public record Address(String value) {

    public Address(String value) {
        if (value == null) {
            throw new InvalidFieldException("address", "REQUIRED", "Address is required");
        }

        String normalized = value.trim();

        if (normalized.isBlank()) {
            throw new InvalidFieldException("address", "REQUIRED", "Address is required");
        }

        if (normalized.length() < 5) {
            throw new InvalidFieldException("address", "INVALID_FORMAT", "Address is too short");
        }

        if (normalized.length() > 255) {
            throw new InvalidFieldException("address", "INVALID_FORMAT", "Address is too long");
        }

        this.value = normalized;
    }
}