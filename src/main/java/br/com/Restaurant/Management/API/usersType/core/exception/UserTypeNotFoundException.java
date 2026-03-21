package br.com.Restaurant.Management.API.usersType.core.exception;

public class UserTypeNotFoundException extends RuntimeException {
    public UserTypeNotFoundException(Long id) {
        super("User type not found with id: " + id);
    }
}
