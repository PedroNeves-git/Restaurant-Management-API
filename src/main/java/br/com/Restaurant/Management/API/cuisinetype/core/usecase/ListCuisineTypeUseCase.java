package br.com.Restaurant.Management.API.cuisinetype.core.usecase;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;

import java.util.List;

public class ListCuisineTypeUseCase {

    private final CuisineTypeGateway cuisineTypeGateway;

    public ListCuisineTypeUseCase(CuisineTypeGateway cuisineTypeGateway) {
        this.cuisineTypeGateway = cuisineTypeGateway;
    }

    public PaginatedResponseDTO<CuisineTypeOutputDTO> execute(int page, int size) {
        PaginatedResponseDTO<CuisineType> cuisinesType =  cuisineTypeGateway.findAll(page, size);

        List<CuisineTypeOutputDTO> content = cuisinesType.content().stream()
                .map(CuisineType::toOutput)
                .toList();

        return new PaginatedResponseDTO<>(
                content,
                cuisinesType.page(),
                cuisinesType.size(),
                cuisinesType.totalElements(),
                cuisinesType.totalPages()
        );
    }
}
