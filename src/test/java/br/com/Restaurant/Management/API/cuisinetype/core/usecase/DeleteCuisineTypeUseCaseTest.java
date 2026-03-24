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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DeleteCuisineTypeUseCaseTest {
    private final static long VALID_CUISINE_TYPE_ID = 1L;

    @InjectMocks
    private DeleteCuisineTypeUseCase useCaseUnderTest;

    @Mock
    private CuisineTypeGateway cuisineTypeGateway;

    @Test
    @DisplayName("Should delete cuisine type when id exists")
    void shouldDeleteCuisineTypeWhenIdExists() {
        var cuisineType = CuisineType.restore(VALID_CUISINE_TYPE_ID, "Chinese");

        when(cuisineTypeGateway.findById(anyLong())).thenReturn(Optional.of(cuisineType));

        useCaseUnderTest.execute(VALID_CUISINE_TYPE_ID);

        verify(cuisineTypeGateway, times(1)).findById(anyLong());
        verify(cuisineTypeGateway, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception when cuisine type not found")
    void shouldThrowExceptionWhenCuisineTypeNotFound() {
        when(cuisineTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CuisineTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(VALID_CUISINE_TYPE_ID)
        );

        verify(cuisineTypeGateway, times(1)).findById(anyLong());
        verify(cuisineTypeGateway, never()).deleteById(any());
    }

}
