package br.com.Restaurant.Management.API.cuisinetype.core.usecase;


import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.input.CreateCuisineTypeInputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeAlreadyInUseException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCuisineTypeUseCaseTest {

    private final static String VALID_CUISINE_TYPE_NAME = "Japonese";
    private final static long VALID_CUISINE_TYPE_ID = 1L;

    @InjectMocks
    private CreateCuisineTypeUseCase useCaseUnderTest;

    @Mock
    private CuisineTypeGateway cuisineTypeGateway;


    @Test
    @DisplayName("Should create cuisine type when name does not exist")
    void shouldCreateCuisineTypeWhenNameDoesNotExist() {
        var input = new CreateCuisineTypeInputDTO(VALID_CUISINE_TYPE_NAME);
        var saved = CuisineType.restore(VALID_CUISINE_TYPE_ID, VALID_CUISINE_TYPE_NAME);

        when(cuisineTypeGateway.existsByName(anyString())).thenReturn(false);
        when(cuisineTypeGateway.create(any())).thenReturn(saved);

        var result = useCaseUnderTest.execute(input);

        assertNotNull(result);
        assertEquals(VALID_CUISINE_TYPE_ID, result.id());
        assertEquals(VALID_CUISINE_TYPE_NAME, result.name());

        verify(cuisineTypeGateway).existsByName(anyString());
        verify(cuisineTypeGateway).create(any());
    }

    @Test
    @DisplayName("Should throw exception when cuisine type name already exists")
    void shouldThrowCuisineTypeAlreadyInUseExceptionWhenCuisineTypeNameAlreadyExists() {
        var input = new CreateCuisineTypeInputDTO(VALID_CUISINE_TYPE_NAME);

        when(cuisineTypeGateway.existsByName(anyString())).thenReturn(true);

        assertThrows(CuisineTypeAlreadyInUseException.class,
                    () -> useCaseUnderTest.execute(input)
        );

        verify(cuisineTypeGateway, times(1)).existsByName(anyString());
        verify(cuisineTypeGateway, never()).create(any());
    }

}
