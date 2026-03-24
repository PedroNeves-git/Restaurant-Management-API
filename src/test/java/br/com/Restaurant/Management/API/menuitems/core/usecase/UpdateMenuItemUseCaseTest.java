package br.com.Restaurant.Management.API.menuitems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.input.UpdateMenuItemInputDTO;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.exception.MenuItemNotFoundException;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.core.usecase.UpdateMenuItemUseCase;
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
    @DisplayName("Should update all fields when valid data is provided")
    void shouldUpdateMenuItemWhenItemExists() {
        var input = createInput();
        var menuItem = MenuItem.restore(ID, "OldName", "Old description", BigDecimal.TEN, false, "old.png", RESTAURANT_ID);

        when(menuItemGateway.findById(ID)).thenReturn(Optional.of(menuItem));
        when(menuItemGateway.update(any(MenuItem.class))).thenAnswer(i -> i.getArgument(0));

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(NAME, result.name());
        assertEquals(PRICE, result.price());
        assertTrue(result.availableOnlyInRestaurant());

        verify(menuItemGateway).findById(ID);
        verify(menuItemGateway).update(menuItem);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemDoesNotExist() {
        var input = createInput();
        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> useCaseUnderTest.execute(ID, input));

        verify(menuItemGateway).findById(anyLong());
        verifyNoMoreInteractions(menuItemGateway);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço no input for inválido (cobre requirePrice)")
    void shouldThrowExceptionWhenPriceIsInvalid() {
        var inputComPrecoInvalido = new UpdateMenuItemInputDTO(NAME, DESCRIPTION, BigDecimal.ZERO, AVAILABLE, IMAGE);
        var menuItem = MenuItem.restore(ID, "Old", "Old", BigDecimal.TEN, false, "old.png", RESTAURANT_ID);

        when(menuItemGateway.findById(ID)).thenReturn(Optional.of(menuItem));

        var exception = assertThrows(IllegalArgumentException.class, () ->
                useCaseUnderTest.execute(ID, inputComPrecoInvalido)
        );

        assertEquals("Price must be greater than zero", exception.getMessage());
        verify(menuItemGateway, never()).update(any());
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