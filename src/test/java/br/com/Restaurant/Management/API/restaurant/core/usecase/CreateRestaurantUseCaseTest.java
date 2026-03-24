package br.com.Restaurant.Management.API.restaurant.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.dto.input.CreateRestaurantInputDTO;
import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.exception.NameAlreadyInUseException;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateRestaurantUseCaseTest {

    private static final Long USER_ID = 1L;
    private static final Long CUISINE_TYPE_ID = 2L;
    private static final String NAME = "Restaurant Name";
    private static final String ADDRESS = "123 Main St";
    private static final LocalTime OPENING_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0);

    @InjectMocks
    private CreateRestaurantUseCase useCaseUnderTest;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @Mock
    private CuisineTypeGateway cuisineTypeGateway;


    @Test
    void shouldCreateRestaurantSuccessfully() {
        var input = createInput();
        var user = mock(User.class);
        var cuisineType = mock(CuisineType.class);
        var createdRestaurant = mock(Restaurant.class);
        var expectedOutput = createOutput();

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.existsByName(NAME)).thenReturn(false);
        when(cuisineTypeGateway.findById(CUISINE_TYPE_ID)).thenReturn(Optional.of(cuisineType));
        when(restaurantGateway.create(any(Restaurant.class))).thenReturn(createdRestaurant);
        when(createdRestaurant.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userGateway).findById(USER_ID);
        verify(restaurantGateway).existsByName(NAME);
        verify(cuisineTypeGateway).findById(CUISINE_TYPE_ID);
        verify(restaurantGateway).create(any(Restaurant.class));
        verify(createdRestaurant).toOutput();
        verifyNoMoreInteractions(restaurantGateway, userGateway, cuisineTypeGateway, createdRestaurant);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        var input = createInput();

        when(userGateway.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCaseUnderTest.execute(input));

        verify(userGateway).findById(USER_ID);
        verifyNoMoreInteractions(userGateway);
        verifyNoInteractions(restaurantGateway, cuisineTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenNameIsAlreadyInUse() {
        var input = createInput();
        var user = mock(User.class);

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.existsByName(NAME)).thenReturn(true);

        assertThrows(NameAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(input));

        verify(userGateway).findById(USER_ID);
        verify(restaurantGateway).existsByName(NAME);
        verifyNoMoreInteractions(userGateway, restaurantGateway);
        verifyNoInteractions(cuisineTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenCuisineTypeDoesNotExist() {
        var input = createInput();
        var user = mock(User.class);

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.existsByName(NAME)).thenReturn(false);
        when(cuisineTypeGateway.findById(CUISINE_TYPE_ID)).thenReturn(Optional.empty());

        assertThrows(CuisineTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(input));

        verify(userGateway).findById(USER_ID);
        verify(restaurantGateway).existsByName(NAME);
        verify(cuisineTypeGateway).findById(CUISINE_TYPE_ID);
        verifyNoMoreInteractions(userGateway, restaurantGateway, cuisineTypeGateway);
    }


    private CreateRestaurantInputDTO createInput() {
        return new CreateRestaurantInputDTO(
                NAME,
                ADDRESS,
                OPENING_TIME.toString(),
                CLOSING_TIME.toString(),
                CUISINE_TYPE_ID,
                USER_ID
        );
    }

    private RestaurantOutputDTO createOutput() {
        return new RestaurantOutputDTO(
                1L,
                NAME,
                ADDRESS,
                OPENING_TIME,
                CLOSING_TIME,
                CUISINE_TYPE_ID,
                USER_ID
        );
    }
}
