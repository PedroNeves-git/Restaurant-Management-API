package br.com.Restaurant.Management.API.restaurant.core.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
