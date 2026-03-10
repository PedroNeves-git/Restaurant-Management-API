package br.com.Restaurant.Management.API.userstype.core.exception;

public class UserTypeAlreadyInUseException extends RuntimeException {
    public UserTypeAlreadyInUseException(String name) {
        super("User type already exists: " + name);
    }
}
