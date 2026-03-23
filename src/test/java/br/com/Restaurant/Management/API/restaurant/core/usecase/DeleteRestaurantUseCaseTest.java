package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteRestaurantUseCaseTest {

    private static final Long ID = 1L;

    @InjectMocks
    private DeleteRestaurantUseCase useCaseUnderTest;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Test
    void shouldDeleteRestaurantWhenItExists() {
        var restaurant = mock(Restaurant.class);

        when(restaurantGateway.findById(ID)).thenReturn(Optional.of(restaurant));

        useCaseUnderTest.execute(ID);

        verify(restaurantGateway).findById(ID);
        verify(restaurantGateway).deleteById(ID);
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {
        when(restaurantGateway.findById(ID)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> useCaseUnderTest.execute(ID));

        verify(restaurantGateway).findById(ID);
        verifyNoMoreInteractions(restaurantGateway);
    }
}
