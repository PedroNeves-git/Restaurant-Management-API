package br.com.Restaurant.Management.API.menuitems.core.usecase;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.exception.MenuItemNotFoundException;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.core.usecase.DeleteMenuItemUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseTest {
    private final Long ID = 1L;

    @InjectMocks
    private DeleteMenuItemUseCase useCaseUnderTest;

    @Mock
    private MenuItemGateway menuItemGateway;


    @Test
    void shouldDeleteMenuItemWhenItemExists() {
        var menuItem = mock(MenuItem.class);

        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.of(menuItem));

        useCaseUnderTest.execute(ID);

        verify(menuItemGateway).findById(anyLong());
        verify(menuItemGateway).deleteById(anyLong());
        verifyNoMoreInteractions(menuItemGateway);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemDoesNotExist() {
        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                        () -> useCaseUnderTest.execute(ID));

        verify(menuItemGateway).findById(anyLong());
        verify(menuItemGateway, never()).deleteById(any());
        verifyNoMoreInteractions(menuItemGateway);
    }
}
