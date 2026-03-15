package br.com.Restaurant.Management.API.restaurant.core.exception;

public class NameAlreadyInUseException extends RuntimeException {
    public NameAlreadyInUseException(String name) {
        super("Name already exists: " + name);
    }
}
