package br.com.Restaurant.Management.API.common.dto;

import java.util.List;

public record PaginatedResponseDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}