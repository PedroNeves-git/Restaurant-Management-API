package br.com.Restaurant.Management.API.restaurant.core.domain.vo;

import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record BusinessTime(LocalTime value) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static BusinessTime openingTime(String value) {
        return new BusinessTime(parse(value, "openingTime", "Opening time"));
    }

    public static BusinessTime closingTime(String value) {
        return new BusinessTime(parse(value, "closingTime", "Closing time"));
    }

    private static LocalTime parse(String value, String field, String label) {
        if (value == null) {
            throw new InvalidFieldException(field, "REQUIRED", label + " is required");
        }

        String normalized = value.trim();

        if (normalized.isBlank()) {
            throw new InvalidFieldException(field, "REQUIRED", label + " is required");
        }

        try {
            return LocalTime.parse(normalized, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidFieldException(field, "INVALID_FORMAT", "Invalid time format, expected HH:mm");
        }
    }
}
