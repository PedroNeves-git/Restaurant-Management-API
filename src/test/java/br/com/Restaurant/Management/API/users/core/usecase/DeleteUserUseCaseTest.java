package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseTest {

    private static final Long ID = 1L;

    @InjectMocks
    private DeleteUserUseCase useCaseUnderTest;

    @Mock
    private UserGateway userGateway;


    @Test
    void shouldDeleteUserWhenItExists() {
        var user = mock(User.class);

        when(userGateway.findById(ID)).thenReturn(Optional.of(user));

        useCaseUnderTest.execute(ID);

        verify(userGateway).findById(ID);
        verify(userGateway).deleteById(ID);
        verifyNoMoreInteractions(userGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userGateway.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCaseUnderTest.execute(ID));

        verify(userGateway).findById(ID);
        verifyNoMoreInteractions(userGateway);
    }
}
