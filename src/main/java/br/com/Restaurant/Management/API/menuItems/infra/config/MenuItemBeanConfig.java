package br.com.Restaurant.Management.API.menuItems.infra.config;

import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.core.usecase.*;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemBeanConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        return new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(MenuItemGateway gateway) {
        return new UpdateMenuItemUseCase(gateway);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(MenuItemGateway gateway) {
        return new DeleteMenuItemUseCase(gateway);
    }

    @Bean
    public ListMenuItemByIdUseCase listMenuItemByIdUseCase(MenuItemGateway gateway) {
        return new ListMenuItemByIdUseCase(gateway);
    }

    @Bean
    public ListMenuItemsUseCase listMenuItemsUseCase(MenuItemGateway gateway) {
        return new ListMenuItemsUseCase(gateway);
    }
}