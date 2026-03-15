package br.com.Restaurant.Management.API.restaurant.core.gateway;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {
    Restaurant create(Restaurant user);
    Restaurant update(Restaurant user);
    Optional<Restaurant> findById(Long id);
    void deleteById(Long id);

    boolean existsByName(String name);
    List<Restaurant> findRestaurantsByOwnerId(Long ownerId);
    PaginatedResponseDTO<Restaurant> findAll(int page, int size);
}
