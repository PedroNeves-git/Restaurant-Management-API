package br.com.Restaurant.Management.API.cuisinetype.core.gateway;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;

import java.util.Optional;

public interface CuisineTypeGateway {
    CuisineType create(CuisineType userType);
    void deleteById(Long id);
    Optional<CuisineType> findById(Long id);

    boolean existsByName(String name);
    PaginatedResponseDTO<CuisineType> findAll(int page, int size);
}
