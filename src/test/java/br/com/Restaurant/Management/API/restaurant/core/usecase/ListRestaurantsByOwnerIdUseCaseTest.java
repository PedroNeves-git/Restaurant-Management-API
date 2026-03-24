package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
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
public class ListRestaurantsByOwnerIdUseCaseTest {

    private static final Long OWNER_ID = 1L;

    @InjectMocks
    private ListRestaurantsByOwnerIdUseCase useCaseUnderTest;

    @Mock
    private RestaurantGateway restaurantGateway;


    @Test
    void shouldReturnRestaurantsWhenOwnerHasRestaurants() {
        var restaurant1 = mock(Restaurant.class);
        var restaurant2 = mock(Restaurant.class);
        var output1 = createOutput(1L);
        var output2 = createOutput(2L);

        when(restaurantGateway.findRestaurantsByRestaurantOwnerId(OWNER_ID))
                .thenReturn(List.of(restaurant1, restaurant2));
        when(restaurant1.toOutput()).thenReturn(output1);
        when(restaurant2.toOutput()).thenReturn(output2);

        var result = useCaseUnderTest.execute(OWNER_ID);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(List.of(output1, output2), result);

        verify(restaurantGateway).findRestaurantsByRestaurantOwnerId(OWNER_ID);
        verify(restaurant1).toOutput();
        verify(restaurant2).toOutput();
        verifyNoMoreInteractions(restaurantGateway, restaurant1, restaurant2);
    }

    @Test
    void shouldThrowExceptionWhenOwnerHasNoRestaurants() {
        when(restaurantGateway.findRestaurantsByRestaurantOwnerId(OWNER_ID))
                .thenReturn(Collections.emptyList());

        assertThrows(RestaurantNotFoundException.class,
                () -> useCaseUnderTest.execute(OWNER_ID));

        verify(restaurantGateway).findRestaurantsByRestaurantOwnerId(OWNER_ID);
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
                OWNER_ID
        );
    }
}