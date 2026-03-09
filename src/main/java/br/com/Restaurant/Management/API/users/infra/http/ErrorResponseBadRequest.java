package br.com.Restaurant.Management.API.users.infra.http;

import java.time.OffsetDateTime;

public record ErrorResponseBadRequest(
        int status,
        String code,
        String field,
        String message,
        OffsetDateTime timestamp
) { }