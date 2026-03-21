package br.com.Restaurant.Management.API.menuItems.infra.repository;

import br.com.Restaurant.Management.API.menuItems.infra.entity.MenuItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, Long> {
    Page<MenuItemEntity> findAllByRestaurantId(Long restaurantId, Pageable pageable);
}