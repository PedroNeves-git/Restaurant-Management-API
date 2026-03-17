package br.com.Restaurant.Management.API.cuisinetype.core.exception;

public class CuisineTypeAlreadyInUseException extends RuntimeException {
    public CuisineTypeAlreadyInUseException(String name) {
        super("Cuisine type already exists: " + name);
    }
}
