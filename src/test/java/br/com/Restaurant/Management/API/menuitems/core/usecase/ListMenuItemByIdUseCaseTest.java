package br.com.Restaurant.Management.API.menuitems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.exception.MenuItemNotFoundException;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.core.usecase.ListMenuItemByIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMenuItemByIdUseCaseTest {
    private final Long ID = 1L;

    @InjectMocks
    private ListMenuItemByIdUseCase useCaseUnderTest;

    @Mock
    private MenuItemGateway menuItemGateway;


    @Test
    void shouldReturnMenuItemWhenItemExists() {
        var menuItem = mock(MenuItem.class);
        var expectedOutput = new MenuItemOutputDTO(
                ID,
                "Pizza",
                "Delicious pizza",
                null,
                true,
                "image.png",
                10L
        );

        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.of(menuItem));
        when(menuItem.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(menuItemGateway).findById(anyLong());
        verify(menuItem).toOutput();
        verifyNoMoreInteractions(menuItemGateway, menuItem);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemDoesNotExist() {
        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                     () -> useCaseUnderTest.execute(ID));

        verify(menuItemGateway).findById(anyLong());
        verifyNoMoreInteractions(menuItemGateway);
    }

}
