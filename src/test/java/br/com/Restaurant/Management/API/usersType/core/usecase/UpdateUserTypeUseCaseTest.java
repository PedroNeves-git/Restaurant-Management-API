package br.com.Restaurant.Management.API.usersType.core.usecase;

import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.dto.UserTypePersistenceDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.input.UpdateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class UpdateUserTypeUseCaseTest {

    private static final Long ID = 1L;
    private static final String NAME = "Owner";

    @InjectMocks
    private UpdateUserTypeUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;


    @Test
    void shouldUpdateUserTypeWhenItemExists() {
        var input = createInput();
        var userType = mock(UserType.class);
        var updatedUserType = mock(UserType.class);
        var expectedOutput = createOutput();
        var persistenceDTO = new UserTypePersistenceDTO(1L, NAME);

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.of(userType));
        when(userType.export()).thenReturn(persistenceDTO); // <-- add this
        when(userTypeGateway.update(userType)).thenReturn(updatedUserType);
        when(updatedUserType.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userTypeGateway).findById(anyLong());
        verify(userType).update(
                input.name()
        );

        verify(userTypeGateway).update(userType);
        verify(updatedUserType).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType, updatedUserType);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        var input = createInput();

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(userTypeGateway).findById(anyLong());
        verifyNoMoreInteractions(userTypeGateway);
    }


    private UpdateUserTypeInputDTO createInput() {
        return new UpdateUserTypeInputDTO(
                NAME
        );
    }

    private UserTypeOutputDTO createOutput() {
        return new UserTypeOutputDTO(
                ID,
                NAME
        );
    }
}
