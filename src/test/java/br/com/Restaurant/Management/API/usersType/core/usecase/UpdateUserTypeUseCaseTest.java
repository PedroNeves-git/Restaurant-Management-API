package br.com.Restaurant.Management.API.usersType.core.usecase;

import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.dto.UserTypePersistenceDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.input.UpdateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeAlreadyInUseException;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserTypeUseCaseTest {

    private static final Long ID = 1L;
    private static final String CURRENT_NAME = "Current Name";
    private static final String UPDATED_NAME = "Updated Name";

    @InjectMocks
    private UpdateUserTypeUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;


    @Test
    void shouldUpdateUserTypeSuccessfully() {
        var input = createInput(UPDATED_NAME);
        var userType = mock(UserType.class);
        var persistenceDTO = mock(UserTypePersistenceDTO.class);
        var updatedUserType = mock(UserType.class);
        var expectedOutput = createOutput(UPDATED_NAME);

        when(userTypeGateway.findById(ID)).thenReturn(Optional.of(userType));
        when(userType.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.name()).thenReturn(CURRENT_NAME);
        when(userTypeGateway.existsByName(UPDATED_NAME)).thenReturn(false);
        when(userTypeGateway.update(userType)).thenReturn(updatedUserType);
        when(updatedUserType.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userTypeGateway).findById(ID);
        verify(userType).export();
        verify(userTypeGateway).existsByName(UPDATED_NAME);
        verify(userType).update(input.name());
        verify(userTypeGateway).update(userType);
        verify(updatedUserType).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType, updatedUserType);
    }

    @Test
    void shouldUpdateUserTypeSuccessfullyWhenNameIsUnchanged() {
        var input = createInput(CURRENT_NAME);
        var userType = mock(UserType.class);
        var persistenceDTO = mock(UserTypePersistenceDTO.class);
        var updatedUserType = mock(UserType.class);
        var expectedOutput = createOutput(CURRENT_NAME);

        when(userTypeGateway.findById(ID)).thenReturn(Optional.of(userType));
        when(userType.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.name()).thenReturn(CURRENT_NAME);
        when(userTypeGateway.update(userType)).thenReturn(updatedUserType);
        when(updatedUserType.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userTypeGateway).findById(ID);
        verify(userType).export();
        verify(userTypeGateway, never()).existsByName(any());
        verify(userType).update(input.name());
        verify(userTypeGateway).update(userType);
        verify(updatedUserType).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType, updatedUserType);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        var input = createInput(UPDATED_NAME);

        when(userTypeGateway.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(userTypeGateway).findById(ID);
        verifyNoMoreInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenNameIsAlreadyInUse() {
        var input = createInput(UPDATED_NAME);
        var userType = mock(UserType.class);
        var persistenceDTO = mock(UserTypePersistenceDTO.class);

        when(userTypeGateway.findById(ID)).thenReturn(Optional.of(userType));
        when(userType.export()).thenReturn(persistenceDTO);
        when(persistenceDTO.name()).thenReturn(CURRENT_NAME);
        when(userTypeGateway.existsByName(UPDATED_NAME)).thenReturn(true);

        assertThrows(UserTypeAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(ID, input));

        verify(userTypeGateway).findById(ID);
        verify(userType).export();
        verify(userTypeGateway).existsByName(UPDATED_NAME);
        verifyNoMoreInteractions(userTypeGateway, userType);
    }


    private UpdateUserTypeInputDTO createInput(String name) {
        return new UpdateUserTypeInputDTO(name);
    }

    private UserTypeOutputDTO createOutput(String name) {
        return new UserTypeOutputDTO(ID, name);
    }
}
