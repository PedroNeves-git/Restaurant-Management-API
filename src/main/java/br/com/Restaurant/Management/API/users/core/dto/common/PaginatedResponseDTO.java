package br.com.Restaurant.Management.API.users.core.dto.common;

import java.util.List;

public record PaginatedResponseDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}