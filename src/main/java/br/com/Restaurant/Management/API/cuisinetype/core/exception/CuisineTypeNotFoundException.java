package br.com.Restaurant.Management.API.cuisinetype.core.exception;

public class CuisineTypeNotFoundException extends RuntimeException {
    public CuisineTypeNotFoundException(Long id) {
        super("Cuisine type not found with id: " + id);
    }
}
