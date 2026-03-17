package br.com.Restaurant.Management.API.users.core.domain.enums;

import lombok.Getter;

public enum UserRole {
    RESTAURANT_OWNER("restaurant_owner"),
    USER("user");

    @Getter
    private String role;

    UserRole(String role){
        this.role = role;
    }
}
