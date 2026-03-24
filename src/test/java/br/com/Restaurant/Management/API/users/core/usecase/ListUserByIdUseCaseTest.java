package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListUserByIdUseCaseTest {

    private static final Long ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john@email.com";
    private static final String LOGIN = "johndoe";
    private static final Long USER_TYPE_ID = 1L;

    @InjectMocks
    private ListUserByIdUseCase useCaseUnderTest;

    @Mock
    private UserGateway userGateway;


    @Test
    void shouldReturnUserWhenItExists() {
        var user = mock(User.class);
        var expectedOutput = createOutput();

        when(userGateway.findById(ID)).thenReturn(Optional.of(user));
        when(user.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userGateway).findById(ID);
        verify(user).toOutput();
        verifyNoMoreInteractions(userGateway, user);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userGateway.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCaseUnderTest.execute(ID));

        verify(userGateway).findById(ID);
        verifyNoMoreInteractions(userGateway);
    }


    private UserOutputDTO createOutput() {
        return new UserOutputDTO(
                ID,
                NAME,
                EMAIL,
                LOGIN,
                true,
                USER_TYPE_ID,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}