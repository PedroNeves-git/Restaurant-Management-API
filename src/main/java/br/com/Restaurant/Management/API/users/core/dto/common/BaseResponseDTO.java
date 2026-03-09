package br.com.Restaurant.Management.API.users.core.dto.common;

import java.time.OffsetDateTime;

public record BaseResponseDTO(
        int status,
        String code,
        String message,
        OffsetDateTime timestamp
) {
    public static BaseResponseDTO success(int status, String code, String message) {
        return new BaseResponseDTO(status, code, message, OffsetDateTime.now());
    }
}
