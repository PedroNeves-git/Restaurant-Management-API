package br.com.Restaurant.Management.API.cuisinetype.core.usecase;


import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListCuisineTypeUseCaseTest {
    private final static int PAGE_NUMBER = 0;
    private final static int  PAGE_SIZE = 10;

    @InjectMocks
    private ListCuisineTypeUseCase useCaseUnderTest;

    @Mock
    private CuisineTypeGateway cuisineTypeGateway;

    @Test
    @DisplayName("Should return paginated cuisine types when data exists")
    void shouldReturnPaginatedCuisineTypesWhenDataExists() {
        var cuisine1 = CuisineType.restore(1L, "Chinese");
        var cuisine2 = CuisineType.restore(2L, "Italian");

        var paginated = new PaginatedResponseDTO<>(
                List.of(cuisine1, cuisine2),
                PAGE_NUMBER,
                PAGE_SIZE,
                2L,
                1
        );

        when(cuisineTypeGateway.findAll(PAGE_NUMBER, PAGE_SIZE)).thenReturn(paginated);

        var result = useCaseUnderTest.execute(PAGE_NUMBER, PAGE_SIZE);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals("Chinese", result.content().get(0).name());
        assertEquals("Italian", result.content().get(1).name());

        assertEquals(PAGE_NUMBER, result.page());
        assertEquals(PAGE_SIZE, result.size());
        assertEquals(2L, result.totalElements());
        assertEquals(1, result.totalPages());

        verify(cuisineTypeGateway, times(1)).findAll(anyInt(), anyInt());
    }


    @Test
    @DisplayName("Should return empty paginated list when no data exists")
    void shouldReturnEmptyPaginatedListWhenNoDataExists() {
        var paginated = new PaginatedResponseDTO<CuisineType>(
                List.of(),
                PAGE_NUMBER,
                PAGE_SIZE,
                0L,
                0
        );

        when(cuisineTypeGateway.findAll(PAGE_NUMBER, PAGE_SIZE)).thenReturn(paginated);

        var result = useCaseUnderTest.execute(PAGE_NUMBER, PAGE_SIZE);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(PAGE_NUMBER, result.page());
        assertEquals(PAGE_SIZE, result.size());
        assertEquals(0L, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(cuisineTypeGateway, times(1)).findAll(anyInt(), anyInt());
    }
}