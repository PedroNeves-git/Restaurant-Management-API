package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.RestaurantPersistenceDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.input.UpdateRestaurantInputDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.exception.NameAlreadyInUseException;
import br.com.Restaurant.Management.API.restaurant.core.exception.RestaurantNotFoundException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateRestaurantUseCaseTest {

    private static final Long ID = 1L;
    private static final Long CUISINE_TYPE_ID = 2L;
    private static final String CURRENT_NAME = "Current Name";
    private static final String UPDATED_NAME = "Updated Name";
    private static final String ADDRESS = "123 Main St";
    private static final LocalTime OPENING_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0);

    @InjectMocks
    private UpdateRestaurantUseCase useCaseUnderTest;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private CuisineTypeGateway cuisineTypeGateway;


    @Test
    void shouldUpdateRestaurantSuccessfully() {
        var input = createInput(UPDATED_NAME);
        var restaurant = mock(Restaurant.class);
        var persistenceDTO = mock(RestaurantPersistenceDTO.class);
        var updatedRestaurant = mock(Restaurant.class);
        var expectedOutput = createOutput(UPDATED_NAME);
        var cuisineType = mock(CuisineType.class);

        when(restaurantGateway.findById(ID)).thenReturn(Optional.of(restaurant));
        when(restaurant.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.name()).thenReturn(CURRENT_NAME);
        when(restaurantGateway.existsByName(UPDATED_NAME)).thenReturn(false);
        when(cuisineTypeGateway.findById(CUISINE_TYPE_ID)).thenReturn(Optional.of(cuisineType));
        when(restaurantGateway.update(restaurant)).thenReturn(updatedRestaurant);
        when(updatedRestaurant.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(restaurantGateway).findById(ID);
        verify(restaurant).export();
        verify(restaurantGateway).existsByName(UPDATED_NAME);
        verify(cuisineTypeGateway).findById(CUISINE_TYPE_ID);
        verify(restaurant).update(
                input.name(),
                input.address(),
                input.openingTime(),
                input.closingTime(),
                input.cuisineTypeId()
        );
        verify(restaurantGateway).update(restaurant);
        verify(updatedRestaurant).toOutput();
        verifyNoMoreInteractions(restaurantGateway, cuisineTypeGateway, restaurant, updatedRestaurant);
    }

    @Test
    void shouldUpdateRestaurantSuccessfullyWhenNameIsUnchanged() {
        var input = createInput(CURRENT_NAME);
        var restaurant = mock(Restaurant.class);
        var persistenceDTO = mock(RestaurantPersistenceDTO.class);
        var updatedRestaurant = mock(Restaurant.class);
        var expectedOutput = createOutput(CURRENT_NAME);
        var cuisineType = mock(CuisineType.class);

        when(restaurantGateway.findById(ID)).thenReturn(Optional.of(restaurant));
        when(restaurant.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.name()).thenReturn(CURRENT_NAME);
        when(cuisineTypeGateway.findById(CUISINE_TYPE_ID)).thenReturn(Optional.of(cuisineType));
        when(restaurantGateway.update(restaurant)).thenReturn(updatedRestaurant);
        when(updatedRestaurant.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(restaurantGateway).findById(ID);
        verify(restaurant).export();
        verify(restaurantGateway, never()).existsByName(any());
        verify(cuisineTypeGateway).findById(CUISINE_TYPE_ID);
        verify(restaurant).update(
                input.name(),
                input.address(),
                input.openingTime(),
                input.closingTime(),
                input.cuisineTypeId()
        );
        verify(restaurantGateway).update(restaurant);
        verify(updatedRestaurant).toOutput();
        verifyNoMoreInteractions(restaurantGateway, cuisineTypeGateway, restaurant, updatedRestaurant);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {
        var input = createInput(UPDATED_NAME);

        when(restaurantGateway.findById(ID)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(restaurantGateway).findById(ID);
        verifyNoMoreInteractions(restaurantGateway);
        verifyNoInteractions(cuisineTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenNameIsAlreadyInUse() {
        var input = createInput(UPDATED_NAME);
        var restaurant = mock(Restaurant.class);
        var persistenceDTO = mock(RestaurantPersistenceDTO.class);

        when(restaurantGateway.findById(ID)).thenReturn(Optional.of(restaurant));
        when(restaurant.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.name()).thenReturn(CURRENT_NAME);
        when(restaurantGateway.existsByName(UPDATED_NAME)).thenReturn(true);

        assertThrows(NameAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(restaurantGateway).findById(ID);
        verify(restaurant).export();
        verify(restaurantGateway).existsByName(UPDATED_NAME);
        verifyNoMoreInteractions(restaurantGateway, restaurant);
        verifyNoInteractions(cuisineTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenCuisineTypeDoesNotExist() {
        var input = createInput(UPDATED_NAME);
        var restaurant = mock(Restaurant.class);
        var persistenceDTO = mock(RestaurantPersistenceDTO.class);

        when(restaurantGateway.findById(ID)).thenReturn(Optional.of(restaurant));
        when(restaurant.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.name()).thenReturn(CURRENT_NAME);
        when(restaurantGateway.existsByName(UPDATED_NAME)).thenReturn(false);
        when(cuisineTypeGateway.findById(CUISINE_TYPE_ID)).thenReturn(Optional.empty());

        assertThrows(CuisineTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(restaurantGateway).findById(ID);
        verify(restaurant).export();
        verify(restaurantGateway).existsByName(UPDATED_NAME);
        verify(cuisineTypeGateway).findById(CUISINE_TYPE_ID);
        verifyNoMoreInteractions(restaurantGateway, cuisineTypeGateway, restaurant);
    }


    private UpdateRestaurantInputDTO createInput(String name) {
        return new UpdateRestaurantInputDTO(
                name,
                ADDRESS,
                OPENING_TIME.toString(),
                CLOSING_TIME.toString(),
                CUISINE_TYPE_ID
        );
    }

    private RestaurantOutputDTO createOutput(String name) {
        return new RestaurantOutputDTO(
                ID,
                name,
                ADDRESS,
                OPENING_TIME,
                CLOSING_TIME,
                CUISINE_TYPE_ID,
                1L
        );
    }
}