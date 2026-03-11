package br.com.Restaurant.Management.API.common.dto;

import java.time.OffsetDateTime;

public record ErrorResponseBadRequest(
        int status,
        String code,
        String field,
        String message,
        OffsetDateTime timestamp
) { }