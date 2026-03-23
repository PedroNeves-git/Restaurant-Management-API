package br.com.Restaurant.Management.API.menuitems.core.usecase;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.core.dto.output.MenuItemOutputDTO;
import br.com.Restaurant.Management.API.menuItems.core.gateway.MenuItemGateway;
import br.com.Restaurant.Management.API.menuItems.core.usecase.ListMenuItemsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMenuItemsUseCaseTest {

    private static final int PAGE = 0;
    private static final int SIZE = 10;

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;

    @InjectMocks
    private ListMenuItemsUseCase useCaseUnderTest;

    @Mock
    private MenuItemGateway menuItemGateway;

    @Test
    void shouldReturnPaginatedMenuItems() {
        var output1 = createOutput(ID_1, "Pizza");
        var output2 = createOutput(ID_2, "Burger");

        var item1 = mockMenuItem(output1);
        var item2 = mockMenuItem(output2);

        var gatewayResponse = createPaginatedResponse(
                List.of(item1, item2),
                2,
                1
        );

        when(menuItemGateway.findAll(anyInt(), anyInt())).thenReturn(gatewayResponse);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(output1, result.content().get(0));
        assertEquals(output2, result.content().get(1));
        assertEquals(PAGE, result.page());
        assertEquals(SIZE, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());

        verify(menuItemGateway).findAll(anyInt(), anyInt());
        verify(item1).toOutput();
        verify(item2).toOutput();
        verifyNoMoreInteractions(menuItemGateway, item1, item2);
    }

    @Test
    void shouldReturnEmptyListWhenNoItemsExist() {
        var gatewayResponse = createPaginatedResponse(
                List.of(),
                0,
                0
        );

        when(menuItemGateway.findAll(anyInt(), anyInt())).thenReturn(gatewayResponse);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(menuItemGateway).findAll(anyInt(), anyInt());
        verifyNoMoreInteractions(menuItemGateway);
    }

    private MenuItem mockMenuItem(MenuItemOutputDTO output) {
        var item = mock(MenuItem.class);
        when(item.toOutput()).thenReturn(output);
        return item;
    }

    private MenuItemOutputDTO createOutput(Long id, String name) {
        return new MenuItemOutputDTO(
                id,
                name,
                "Desc",
                null,
                true,
                "img.png",
                1L
        );
    }

    private PaginatedResponseDTO<MenuItem> createPaginatedResponse(
            List<MenuItem> content,
            long totalElements,
            int totalPages
    ) {
        return new PaginatedResponseDTO<>(
                content,
                PAGE,
                SIZE,
                totalElements,
                totalPages
        );
    }
}