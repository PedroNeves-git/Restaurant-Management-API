package br.com.Restaurant.Management.API.menuitems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.input.CreateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.core.usecase.CreateMenuItemUseCase;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMenuItemUseCaseTest {
    private static final Long RESTAURANT_ID = 1L;
    private static final Long MENU_ITEM_ID = 1L;
    private static final String NAME = "Pizza";
    private static final String DESCRIPTION = "Delicious pizza";
    private static final BigDecimal PRICE = new BigDecimal("29.90");
    private static final boolean AVAILABLE_ONLY_IN_RESTAURANT = true;
    private static final String IMAGE_PATH = "/images/pizza.png";

    @InjectMocks
    private CreateMenuItemUseCase useCaseUnderTest;

    @Mock
    private MenuItemGateway menuItemGateway;
    @Mock
    private RestaurantGateway restaurantGateway;

    @Test
    @DisplayName("Should create menu item when restaurant exists")
    void shouldCreateMenuItemWhenRestaurantExists() {
        var input = createValidInput();
        var restaurant = mock(Restaurant.class);
        var createdMenuItem = createMenuItemDomain();

        when(restaurantGateway.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.create(any(MenuItem.class))).thenReturn(createdMenuItem);

        var result = useCaseUnderTest.execute(input);

        assertNotNull(result);
        assertEquals(MENU_ITEM_ID, result.id());
        assertEquals(NAME, result.name());

        verify(restaurantGateway).findById(anyLong());
        verify(menuItemGateway).create(any(MenuItem.class));
    }

    @Test
    @DisplayName("Should throw exception when restaurant does not exist")
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {
        var input = createValidInput();

        when(restaurantGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                     () -> useCaseUnderTest.execute(input)
        );

        verify(restaurantGateway).findById(anyLong());
        verify(menuItemGateway, never()).create(any());
    }



    private CreateMenuItemInputDTO createValidInput() {
        return new CreateMenuItemInputDTO(
                NAME,
                DESCRIPTION,
                PRICE,
                AVAILABLE_ONLY_IN_RESTAURANT,
                IMAGE_PATH,
                RESTAURANT_ID
        );
    }

    private MenuItem createMenuItemDomain() {
        return MenuItem.restore(
                MENU_ITEM_ID,
                NAME,
                DESCRIPTION,
                PRICE,
                AVAILABLE_ONLY_IN_RESTAURANT,
                IMAGE_PATH,
                RESTAURANT_ID
        );
    }
}