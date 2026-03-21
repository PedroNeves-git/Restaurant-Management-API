package br.com.Restaurant.Management.API.menuItems.core.gateway;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;

import java.util.Optional;

public interface MenuItemGateway {
    MenuItem create(MenuItem menuItem);
    MenuItem update(MenuItem menuItem);
    void deleteById(Long id);
    Optional<MenuItem> findById(Long id);
    PaginatedResponseDTO<MenuItem> findAll(int page, int size);
    PaginatedResponseDTO<MenuItem> findAllByRestaurantId(Long restaurantId, int page, int size);
}