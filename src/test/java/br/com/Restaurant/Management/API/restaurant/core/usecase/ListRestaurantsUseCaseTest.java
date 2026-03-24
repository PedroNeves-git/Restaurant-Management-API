package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListRestaurantsUseCaseTest {

    private static final int PAGE = 0;
    private static final int SIZE = 10;
    private static final long TOTAL_ELEMENTS = 2L;
    private static final int TOTAL_PAGES = 1;

    @InjectMocks
    private ListRestaurantsUseCase useCaseUnderTest;

    @Mock
    private RestaurantGateway restaurantGateway;


    @Test
    void shouldReturnPaginatedRestaurants() {
        var restaurant1 = mock(Restaurant.class);
        var restaurant2 = mock(Restaurant.class);
        var output1 = createOutput(1L);
        var output2 = createOutput(2L);

        var paginatedRestaurants = new PaginatedResponseDTO<>(
                List.of(restaurant1, restaurant2),
                PAGE,
                SIZE,
                TOTAL_ELEMENTS,
                TOTAL_PAGES
        );

        when(restaurantGateway.findAll(PAGE, SIZE)).thenReturn(paginatedRestaurants);
        when(restaurant1.toOutput()).thenReturn(output1);
        when(restaurant2.toOutput()).thenReturn(output2);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(List.of(output1, output2), result.content());
        assertEquals(PAGE, result.page());
        assertEquals(SIZE, result.size());
        assertEquals(TOTAL_ELEMENTS, result.totalElements());
        assertEquals(TOTAL_PAGES, result.totalPages());

        verify(restaurantGateway).findAll(PAGE, SIZE);
        verify(restaurant1).toOutput();
        verify(restaurant2).toOutput();
        verifyNoMoreInteractions(restaurantGateway, restaurant1, restaurant2);
    }

    @Test
    void shouldReturnEmptyPaginatedResponseWhenNoRestaurantsExist() {
        var emptyPaginated = new PaginatedResponseDTO<Restaurant>(
                Collections.emptyList(),
                PAGE,
                SIZE,
                0L,
                0
        );

        when(restaurantGateway.findAll(PAGE, SIZE)).thenReturn(emptyPaginated);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(PAGE, result.page());
        assertEquals(SIZE, result.size());
        assertEquals(0L, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(restaurantGateway).findAll(PAGE, SIZE);
        verifyNoMoreInteractions(restaurantGateway);
    }


    private RestaurantOutputDTO createOutput(Long id) {
        return new RestaurantOutputDTO(
                id,
                "Restaurant " + id,
                "Address " + id,
                LocalTime.of(9, 0),
                LocalTime.of(22, 0),
                1L,
                1L
        );
    }
}
