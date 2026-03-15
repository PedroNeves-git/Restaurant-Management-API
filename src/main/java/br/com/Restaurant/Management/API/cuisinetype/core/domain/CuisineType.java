package br.com.Restaurant.Management.API.cuisinetype.core.domain;

import br.com.Restaurant.Management.API.cuisinetype.core.dto.CuisineTypePersistenceDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;

public class CuisineType {
    Long id;
    String name;

    private CuisineType(Long id, String name) {
        this.id = id;
        this.name = requireName(name);
    }

    public static CuisineType newCuisineType(String name) {
        return new CuisineType(null, name);
    }

    public static CuisineType restore(Long id, String name) {
        return new CuisineType(id, name);
    }

    public CuisineTypeOutputDTO toOutput(){
        return new CuisineTypeOutputDTO(id, name);
    }

    public CuisineTypePersistenceDTO export(){
        return new CuisineTypePersistenceDTO(id, name);
    }

    private String requireName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Type Name is required");
        return name;
    }

}
