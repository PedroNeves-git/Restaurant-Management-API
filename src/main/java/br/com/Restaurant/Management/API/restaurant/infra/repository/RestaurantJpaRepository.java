package br.com.Restaurant.Management.API.restaurant.infra.repository;

import br.com.Restaurant.Management.API.restaurant.infra.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Long> {
    boolean existsByName(String name);
    List<RestaurantEntity> findRestaurantsByOwnerId(Long ownerId);
}
