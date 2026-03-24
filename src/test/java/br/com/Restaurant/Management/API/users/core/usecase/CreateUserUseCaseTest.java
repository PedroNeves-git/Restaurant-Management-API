package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.EmailAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.LoginAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class CreateUserUseCaseTest {

    private static final Long USER_TYPE_ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john@email.com";
    private static final String LOGIN = "johndoe";
    private static final String PASSWORD = "Password123";
    private static final String ENCRYPTED_PASSWORD = "encrypted_Password123";
    private static final UserRole ROLE = UserRole.USER;

    @InjectMocks
    private CreateUserUseCase useCaseUnderTest;

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void shouldCreateUserSuccessfully() {
        var input = createInput();
        var userType = mock(UserType.class);
        var createdUser = mock(User.class);
        var expectedOutput = createOutput();

        when(userGateway.existsByEmail(EMAIL)).thenReturn(false);
        when(userGateway.existsByLogin(LOGIN)).thenReturn(false);
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(userGateway.create(any(User.class))).thenReturn(createdUser);
        when(createdUser.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userGateway).existsByEmail(EMAIL);
        verify(userGateway).existsByLogin(LOGIN);
        verify(userTypeGateway).findById(USER_TYPE_ID);
        verify(passwordEncoder).encode(PASSWORD);
        verify(userGateway).create(any(User.class));
        verify(createdUser).toOutput();
        verifyNoMoreInteractions(userGateway, userTypeGateway, passwordEncoder, createdUser);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsAlreadyInUse() {
        var input = createInput();

        when(userGateway.existsByEmail(EMAIL)).thenReturn(true);

        assertThrows(EmailAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(input));

        verify(userGateway).existsByEmail(EMAIL);
        verifyNoMoreInteractions(userGateway);
        verifyNoInteractions(userTypeGateway, passwordEncoder);
    }

    @Test
    void shouldThrowExceptionWhenLoginIsAlreadyInUse() {
        var input = createInput();

        when(userGateway.existsByEmail(EMAIL)).thenReturn(false);
        when(userGateway.existsByLogin(LOGIN)).thenReturn(true);

        assertThrows(LoginAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(input));

        verify(userGateway).existsByEmail(EMAIL);
        verify(userGateway).existsByLogin(LOGIN);
        verifyNoMoreInteractions(userGateway);
        verifyNoInteractions(userTypeGateway, passwordEncoder);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        var input = createInput();

        when(userGateway.existsByEmail(EMAIL)).thenReturn(false);
        when(userGateway.existsByLogin(LOGIN)).thenReturn(false);
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(input));

        verify(userGateway).existsByEmail(EMAIL);
        verify(userGateway).existsByLogin(LOGIN);
        verify(userTypeGateway).findById(USER_TYPE_ID);
        verifyNoMoreInteractions(userGateway, userTypeGateway);
        verifyNoInteractions(passwordEncoder);
    }


    private CreateUserInputDTO createInput() {
        return new CreateUserInputDTO(
                NAME,
                EMAIL,
                LOGIN,
                PASSWORD,
                USER_TYPE_ID,
                ROLE
        );
    }

    private UserOutputDTO createOutput() {
        return new UserOutputDTO(
                1L,
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