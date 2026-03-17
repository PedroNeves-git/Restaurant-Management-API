package br.com.Restaurant.Management.API.restaurant.core.domain;

import br.com.Restaurant.Management.API.restaurant.core.domain.vo.Address;
import br.com.Restaurant.Management.API.restaurant.core.domain.vo.BusinessTime;
import br.com.Restaurant.Management.API.restaurant.core.dto.RestaurantPersistenceDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;

import java.util.Objects;

public class Restaurant {

    private Long id;
    private String name;
    private Address address;
    private BusinessTime openingTime;
    private BusinessTime closingTime;
    private Long cuisineTypeId;
    private Long restaurantOwnerId;

    public Restaurant(Long id, String name, Address address, BusinessTime openingTime, BusinessTime closingTime, Long cuisineTypeId, Long restaurantOwnerId) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.address = Objects.requireNonNull(address);
        this.openingTime = Objects.requireNonNull(openingTime);
        this.closingTime = Objects.requireNonNull(closingTime);
        this.cuisineTypeId = Objects.requireNonNull(cuisineTypeId);
        this.restaurantOwnerId = Objects.requireNonNull(restaurantOwnerId);
    }

    public static Restaurant newRestaurant( String name, String address, String openingTime, String closingTime, Long cuisineTypeId, Long restaurantOwnerId) {
        return new Restaurant(null, name, new Address(address), BusinessTime.openingTime(openingTime), BusinessTime.closingTime(closingTime), cuisineTypeId, restaurantOwnerId);
    }

    public static Restaurant restore(Long id, String name, String address, String openingTime, String closingTime, Long cuisineTypeId, Long restaurantOwnerId) {
        return new Restaurant(id, name, new Address(address), BusinessTime.openingTime(openingTime), BusinessTime.closingTime(closingTime), cuisineTypeId, restaurantOwnerId);
    }

    public void update(String name, String address, String openingTime, String closingTime, Long cuisineTypeId) {
        if (name != null) this.name = requireName(name);
        if (address != null) this.address = new Address(address);
        if (openingTime != null) this.openingTime = BusinessTime.openingTime(openingTime);
        if (closingTime != null) this.closingTime = BusinessTime.closingTime(closingTime);
        if (cuisineTypeId != null) this.cuisineTypeId = cuisineTypeId;
    }

    public RestaurantOutputDTO toOutput() {
        return new RestaurantOutputDTO(id, name, address.value(), openingTime.value(), closingTime.value(), cuisineTypeId, restaurantOwnerId);
    }

    public RestaurantPersistenceDTO export() {
        return new RestaurantPersistenceDTO(id, name, address.value(), openingTime.value(), closingTime.value(), cuisineTypeId, restaurantOwnerId);
    }

    private String requireName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        return name;
    }
}
