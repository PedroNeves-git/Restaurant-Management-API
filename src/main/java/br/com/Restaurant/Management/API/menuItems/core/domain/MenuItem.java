package br.com.Restaurant.Management.API.menuItems.core.domain;

import br.com.Restaurant.Management.API.menuItems.core.dto.MenuItemPersistenceDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;

import java.math.BigDecimal;

public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyInRestaurant;
    private String imagePath;
    private Long restaurantId;

    private MenuItem(Long id, String name, String description, BigDecimal price,
                     Boolean availableOnlyInRestaurant, String imagePath, Long restaurantId) {
        this.id = id;
        this.name = requireName(name);
        this.description = requireDescription(description);
        this.price = requirePrice(price);
        this.availableOnlyInRestaurant = availableOnlyInRestaurant != null ? availableOnlyInRestaurant : false;
        this.imagePath = imagePath;
        this.restaurantId = restaurantId;
    }

    public static MenuItem newMenuItem(String name, String description, BigDecimal price,
                                       Boolean availableOnlyInRestaurant, String imagePath, Long restaurantId) {
        return new MenuItem(null, name, description, price, availableOnlyInRestaurant, imagePath, restaurantId);
    }

    public static MenuItem restore(Long id, String name, String description, BigDecimal price,
                                   Boolean availableOnlyInRestaurant, String imagePath, Long restaurantId) {
        return new MenuItem(id, name, description, price, availableOnlyInRestaurant, imagePath, restaurantId);
    }

    public void update(String name, String description, BigDecimal price,
                       Boolean availableOnlyInRestaurant, String imagePath) {
        if (name != null) this.name = requireName(name);
        if (description != null) this.description = requireDescription(description);
        if (price != null) this.price = requirePrice(price);
        if (availableOnlyInRestaurant != null) this.availableOnlyInRestaurant = availableOnlyInRestaurant;
        if (imagePath != null) this.imagePath = imagePath;
    }

    public MenuItemOutputDTO toOutput() {
        return new MenuItemOutputDTO(id, name, description, price, availableOnlyInRestaurant, imagePath, restaurantId);
    }

    public MenuItemPersistenceDTO export() {
        return new MenuItemPersistenceDTO(id, name, description, price, availableOnlyInRestaurant, imagePath, restaurantId);
    }

    private String requireName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        return name;
    }

    private String requireDescription(String description) {
        if (description == null || description.isBlank()) throw new IllegalArgumentException("Description is required");
        return description;
    }

    private BigDecimal requirePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Price must be greater than zero");
        return price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Boolean getAvailableOnlyInRestaurant() { return availableOnlyInRestaurant; }
    public String getImagePath() { return imagePath; }
    public Long getRestaurantId() { return restaurantId; }
}