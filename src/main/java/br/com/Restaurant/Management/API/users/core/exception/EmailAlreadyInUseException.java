package br.com.Restaurant.Management.API.users.core.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super("Email already exists: " + email);
    }
}
