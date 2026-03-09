package br.com.Restaurant.Management.API.users.infra.http;

import java.time.OffsetDateTime;

public record ErrorResponse(
        int status,
        String code,
        String message,
        OffsetDateTime timestamp
) { }
