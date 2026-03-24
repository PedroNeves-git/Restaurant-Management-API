package br.com.Restaurant.Management.API.cuisinetype.core.usecase;


import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListCuisineTypeGatewayByIdUseCaseTest {
    private final static long VALID_CUISINE_TYPE_ID = 1L;
    private final static String VALID_CUISINE_TYPE_NAME = "Japonese";

    @InjectMocks
    private ListCuisineTypeGatewayByIdUseCase useCaseUnderTest;

    @Mock
    private CuisineTypeGateway cuisineTypeGateway;

    @Test
    @DisplayName("Should return cuisine type when id exists")
    void shouldReturnCuisineTypeWhenIdExists() {
        var cuisineType = CuisineType.restore(VALID_CUISINE_TYPE_ID, VALID_CUISINE_TYPE_NAME);

        when(cuisineTypeGateway.findById(anyLong())).thenReturn(Optional.of(cuisineType));

        var result = useCaseUnderTest.execute(VALID_CUISINE_TYPE_ID);

        assertNotNull(result);
        assertEquals(VALID_CUISINE_TYPE_ID, result.id());
        assertEquals(VALID_CUISINE_TYPE_NAME, result.name());

        verify(cuisineTypeGateway, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception when cuisine type not found")
    void shouldThrowExceptionWhenCuisineTypeNotFound() {
        when(cuisineTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CuisineTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(VALID_CUISINE_TYPE_ID)
        );

        verify(cuisineTypeGateway, times(1)).findById(anyLong());
    }
}