package br.com.Restaurant.Management.API.common.dto;

import java.time.OffsetDateTime;

public record ErrorResponse(
        int status,
        String code,
        String message,
        OffsetDateTime timestamp
) { }
