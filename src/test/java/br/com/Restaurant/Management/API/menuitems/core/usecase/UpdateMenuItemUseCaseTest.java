package br.com.Restaurant.Management.API.menuitems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.input.UpdateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.exception.MenuItemNotFoundException;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.core.usecase.UpdateMenuItemUseCase;
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
class UpdateMenuItemUseCaseTest {
    private static final Long ID = 1L;
    private static final String NAME = "Updated name";
    private static final String DESCRIPTION = "Updated description";
    private static final BigDecimal PRICE = BigDecimal.valueOf(50);
    private static final Boolean AVAILABLE = true;
    private static final String IMAGE = "updated.png";
    private static final Long RESTAURANT_ID = 10L;
    @InjectMocks
    private UpdateMenuItemUseCase useCaseUnderTest;

    @Mock
    private MenuItemGateway menuItemGateway;


    @Test
    void shouldUpdateMenuItemWhenItemExists() {
        var input = createInput();
        var menuItem = mock(MenuItem.class);
        var updatedMenuItem = mock(MenuItem.class);
        var expectedOutput = createOutput();

        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.of(menuItem));
        when(menuItemGateway.update(menuItem)).thenReturn(updatedMenuItem);
        when(updatedMenuItem.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(menuItemGateway).findById(anyLong());
        verify(menuItem).update(
                input.name(),
                input.description(),
                input.price(),
                input.availableOnlyInRestaurant(),
                input.imagePath()
        );

        verify(menuItemGateway).update(menuItem);
        verify(updatedMenuItem).toOutput();
        verifyNoMoreInteractions(menuItemGateway, menuItem, updatedMenuItem);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemDoesNotExist() {
        var input = createInput();

        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(menuItemGateway).findById(anyLong());
        verifyNoMoreInteractions(menuItemGateway);
    }


    private UpdateMenuItemInputDTO createInput() {
        return new UpdateMenuItemInputDTO(
                NAME,
                DESCRIPTION,
                PRICE,
                AVAILABLE,
                IMAGE
        );
    }

    private MenuItemOutputDTO createOutput() {
        return new MenuItemOutputDTO(
                ID,
                NAME,
                DESCRIPTION,
                PRICE,
                AVAILABLE,
                IMAGE,
                RESTAURANT_ID
        );
    }
}