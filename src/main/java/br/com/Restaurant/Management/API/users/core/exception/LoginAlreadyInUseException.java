package br.com.Restaurant.Management.API.users.core.exception;

public class LoginAlreadyInUseException extends RuntimeException {
    public LoginAlreadyInUseException(String login) {
        super("Login already exists: " + login);
    }
}
