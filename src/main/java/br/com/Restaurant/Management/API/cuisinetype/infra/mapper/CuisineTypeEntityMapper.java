package br.com.Restaurant.Management.API.cuisinetype.infra.mapper;

import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.infra.entity.CuisineTypeEntity;

public class CuisineTypeEntityMapper {

    public static CuisineTypeEntity toEntity(CuisineType cuisineType) {

        var data = cuisineType.export();

        return new CuisineTypeEntity(
                data.id(),
                data.name()
        );
    }

    public static CuisineType toDomain(CuisineTypeEntity cuisineTypeEntity) {
        return CuisineType.restore(
                cuisineTypeEntity.getId(),
                cuisineTypeEntity.getName()
        );
    }
}
