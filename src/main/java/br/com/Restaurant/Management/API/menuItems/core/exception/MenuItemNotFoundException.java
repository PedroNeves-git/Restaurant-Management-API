package br.com.Restaurant.Management.API.menuItems.core.exception;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(Long id) {
        super("Menu item not found with id: " + id);
    }
}