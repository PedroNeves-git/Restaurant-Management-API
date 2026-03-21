package br.com.Restaurant.Management.API.menuItems.infra.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyInRestaurant;
    private String imagePath;
    private Long restaurantId;

    public MenuItemEntity() {}

    public MenuItemEntity(Long id, String name, String description, BigDecimal price,
                          Boolean availableOnlyInRestaurant, String imagePath, Long restaurantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyInRestaurant = availableOnlyInRestaurant;
        this.imagePath = imagePath;
        this.restaurantId = restaurantId;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Boolean getAvailableOnlyInRestaurant() { return availableOnlyInRestaurant; }
    public String getImagePath() { return imagePath; }
    public Long getRestaurantId() { return restaurantId; }
}