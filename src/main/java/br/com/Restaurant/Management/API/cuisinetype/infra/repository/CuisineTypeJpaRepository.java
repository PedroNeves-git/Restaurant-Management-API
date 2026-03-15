package br.com.Restaurant.Management.API.cuisinetype.infra.repository;

import br.com.Restaurant.Management.API.cuisinetype.infra.entity.CuisineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineTypeJpaRepository extends JpaRepository<CuisineTypeEntity, Long> {
    boolean existsByName(String name);
}
