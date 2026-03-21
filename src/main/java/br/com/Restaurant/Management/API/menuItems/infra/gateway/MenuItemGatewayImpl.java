package br.com.Restaurant.Management.API.menuItems.infra.gateway;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.infra.entity.MenuItemEntity;
import br.com.Restaurant.Management.API.menuItems.infra.mapper.MenuItemEntityMapper;
import br.com.Restaurant.Management.API.menuItems.infra.repository.MenuItemJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MenuItemGatewayImpl implements MenuItemGateway {

    private final MenuItemJpaRepository repository;

    public MenuItemGatewayImpl(MenuItemJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MenuItem create(MenuItem menuItem) {
        MenuItemEntity entity = MenuItemEntityMapper.toEntity(menuItem);
        return MenuItemEntityMapper.toDomain(repository.save(entity));
    }

    @Override
    public MenuItem update(MenuItem menuItem) {
        MenuItemEntity entity = MenuItemEntityMapper.toEntity(menuItem);
        return MenuItemEntityMapper.toDomain(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return repository.findById(id).map(MenuItemEntityMapper::toDomain);
    }

    @Override
    public PaginatedResponseDTO<MenuItem> findAll(int page, int size) {
        Page<MenuItemEntity> result = repository.findAll(PageRequest.of(page, size));
        List<MenuItem> content = result.getContent().stream()
                .map(MenuItemEntityMapper::toDomain).toList();
        return new PaginatedResponseDTO<>(content, result.getNumber(), result.getSize(),
                result.getTotalElements(), result.getTotalPages());
    }

    @Override
    public PaginatedResponseDTO<MenuItem> findAllByRestaurantId(Long restaurantId, int page, int size) {
        Page<MenuItemEntity> result = repository.findAllByRestaurantId(restaurantId, PageRequest.of(page, size));
        List<MenuItem> content = result.getContent().stream()
                .map(MenuItemEntityMapper::toDomain).toList();
        return new PaginatedResponseDTO<>(content, result.getNumber(), result.getSize(),
                result.getTotalElements(), result.getTotalPages());
    }
}