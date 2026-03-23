package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.UserPersistenceDTO;
import br.com.Restaurant.Management.API.users.core.dto.input.UpdateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.EmailAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.LoginAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;

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
public class UpdateUserUseCaseTest {

    private static final Long ID = 1L;
    private static final Long USER_TYPE_ID = 2L;
    private static final String CURRENT_EMAIL = "current@email.com";
    private static final String UPDATED_EMAIL = "updated@email.com";
    private static final String CURRENT_LOGIN = "currentlogin";
    private static final String UPDATED_LOGIN = "updatedlogin";
    private static final String NAME = "John Doe";
    private static final boolean ACTIVE = true;

    @InjectMocks
    private UpdateUserUseCase useCaseUnderTest;

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;


    @Test
    void shouldUpdateUserSuccessfully() {
        var input = createInput(UPDATED_EMAIL, UPDATED_LOGIN);
        var user = mock(User.class);
        var persistenceDTO = mock(UserPersistenceDTO.class);
        var userType = mock(UserType.class);
        var updatedUser = mock(User.class);
        var expectedOutput = createOutput();

        when(userGateway.findById(ID)).thenReturn(Optional.of(user));
        when(user.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.email()).thenReturn(CURRENT_EMAIL);
        when(persistenceDTO.login()).thenReturn(CURRENT_LOGIN);
        when(userGateway.existsByEmail(UPDATED_EMAIL)).thenReturn(false);
        when(userGateway.existsByLogin(UPDATED_LOGIN)).thenReturn(false);
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));
        when(userGateway.update(user)).thenReturn(updatedUser);
        when(updatedUser.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userGateway).findById(ID);
        verify(user, times(2)).export();
        verify(userGateway).existsByEmail(UPDATED_EMAIL);
        verify(userGateway).existsByLogin(UPDATED_LOGIN);
        verify(userTypeGateway).findById(USER_TYPE_ID);
        verify(user).update(input.name(), input.email(), input.login(), input.typeId(), input.active());
        verify(userGateway).update(user);
        verify(updatedUser).toOutput();
        verifyNoMoreInteractions(userGateway, userTypeGateway, user, updatedUser);
    }

    @Test
    void shouldUpdateUserSuccessfullyWhenEmailAndLoginAreUnchanged() {
        var input = createInput(CURRENT_EMAIL, CURRENT_LOGIN);
        var user = mock(User.class);
        var persistenceDTO = mock(UserPersistenceDTO.class);
        var userType = mock(UserType.class);
        var updatedUser = mock(User.class);
        var expectedOutput = createOutput();

        when(userGateway.findById(ID)).thenReturn(Optional.of(user));
        when(user.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.email()).thenReturn(CURRENT_EMAIL);
        when(persistenceDTO.login()).thenReturn(CURRENT_LOGIN);
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));
        when(userGateway.update(user)).thenReturn(updatedUser);
        when(updatedUser.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userGateway).findById(ID);
        verify(user, times(2)).export();
        verify(userGateway, never()).existsByEmail(any());
        verify(userGateway, never()).existsByLogin(any());
        verify(userTypeGateway).findById(USER_TYPE_ID);
        verify(user).update(input.name(), input.email(), input.login(), input.typeId(), input.active());
        verify(userGateway).update(user);
        verify(updatedUser).toOutput();
        verifyNoMoreInteractions(userGateway, userTypeGateway, user, updatedUser);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        var input = createInput(UPDATED_EMAIL, UPDATED_LOGIN);

        when(userGateway.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(userGateway).findById(ID);
        verifyNoMoreInteractions(userGateway);
        verifyNoInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsAlreadyInUse() {
        var input = createInput(UPDATED_EMAIL, UPDATED_LOGIN);
        var user = mock(User.class);
        var persistenceDTO = mock(UserPersistenceDTO.class);

        when(userGateway.findById(ID)).thenReturn(Optional.of(user));
        when(user.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.email()).thenReturn(CURRENT_EMAIL);
        when(userGateway.existsByEmail(UPDATED_EMAIL)).thenReturn(true);

        assertThrows(EmailAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(userGateway).findById(ID);
        verify(user).export();
        verify(userGateway).existsByEmail(UPDATED_EMAIL);
        verifyNoMoreInteractions(userGateway, user);
        verifyNoInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenLoginIsAlreadyInUse() {
        var input = createInput(UPDATED_EMAIL, UPDATED_LOGIN);
        var user = mock(User.class);
        var persistenceDTO = mock(UserPersistenceDTO.class);

        when(userGateway.findById(ID)).thenReturn(Optional.of(user));
        when(user.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.email()).thenReturn(CURRENT_EMAIL);
        when(persistenceDTO.login()).thenReturn(CURRENT_LOGIN);
        when(userGateway.existsByEmail(UPDATED_EMAIL)).thenReturn(false);
        when(userGateway.existsByLogin(UPDATED_LOGIN)).thenReturn(true);

        assertThrows(LoginAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(userGateway).findById(ID);
        verify(user, times(2)).export();
        verify(userGateway).existsByEmail(UPDATED_EMAIL);
        verify(userGateway).existsByLogin(UPDATED_LOGIN);
        verifyNoMoreInteractions(userGateway, user);
        verifyNoInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        var input = createInput(UPDATED_EMAIL, UPDATED_LOGIN);
        var user = mock(User.class);
        var persistenceDTO = mock(UserPersistenceDTO.class);

        when(userGateway.findById(ID)).thenReturn(Optional.of(user));
        when(user.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.email()).thenReturn(CURRENT_EMAIL);
        when(persistenceDTO.login()).thenReturn(CURRENT_LOGIN);
        when(userGateway.existsByEmail(UPDATED_EMAIL)).thenReturn(false);
        when(userGateway.existsByLogin(UPDATED_LOGIN)).thenReturn(false);
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(userGateway).findById(ID);
        verify(user, times(2)).export();
        verify(userGateway).existsByEmail(UPDATED_EMAIL);
        verify(userGateway).existsByLogin(UPDATED_LOGIN);
        verify(userTypeGateway).findById(USER_TYPE_ID);
        verifyNoMoreInteractions(userGateway, userTypeGateway, user);
    }


    private UpdateUserInputDTO createInput(String email, String login) {
        return new UpdateUserInputDTO(
                NAME,
                email,
                login,
                USER_TYPE_ID,
                ACTIVE
        );
    }

    private UserOutputDTO createOutput() {
        return new UserOutputDTO(
                ID,
                NAME,
                UPDATED_EMAIL,
                UPDATED_LOGIN,
                ACTIVE,
                USER_TYPE_ID,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}