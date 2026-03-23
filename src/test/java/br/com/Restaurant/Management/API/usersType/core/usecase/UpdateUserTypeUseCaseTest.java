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
class UpdateUserTypeUseCaseTest {
    private static final Long ID = 1L;
    private static final String CURRENT_NAME = "ADMIN";
    private static final String NEW_NAME = "CUSTOMER";
    @InjectMocks
    private UpdateUserTypeUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;



    @Test
    void shouldUpdateUserTypeWhenNameIsDifferentAndNotInUse() {
        var userType = mock(UserType.class);
        var input = createInput(NEW_NAME);
        var output = createOutput(NEW_NAME);
        var persistence = mock(UserTypePersistenceDTO.class);

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.of(userType));
        when(userType.export()).thenReturn(persistence);
        when(persistence.name()).thenReturn(CURRENT_NAME);
        when(userTypeGateway.existsByName(anyString())).thenReturn(false);
        when(userTypeGateway.update(userType)).thenReturn(userType);
        when(userType.toOutput()).thenReturn(output);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(NEW_NAME, result.name());

        verify(userTypeGateway).findById(anyLong());
        verify(userType).export();
        verify(userTypeGateway).existsByName(anyString());
        verify(userType).update(NEW_NAME);
        verify(userTypeGateway).update(userType);
        verify(userType).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType, persistence);
    }

    @Test
    void shouldUpdateUserTypeWhenNameIsSame() {
        var userType = mock(UserType.class);
        var input = createInput(CURRENT_NAME);
        var output = createOutput(CURRENT_NAME);
        var persistence = mock(UserTypePersistenceDTO.class);

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.of(userType));
        when(userType.export()).thenReturn(persistence);
        when(persistence.name()).thenReturn(CURRENT_NAME);
        when(userTypeGateway.update(userType)).thenReturn(userType);
        when(userType.toOutput()).thenReturn(output);

        var result = useCaseUnderTest.execute(ID, input);

        assertNotNull(result);
        assertEquals(CURRENT_NAME, result.name());

        verify(userTypeGateway).findById(ID);
        verify(userType).export();
        verify(userType, never()).update(null);
        verify(userType).update(CURRENT_NAME);
        verify(userTypeGateway).update(userType);
        verify(userType).toOutput();

        verify(userTypeGateway, never()).existsByName(anyString());

        verifyNoMoreInteractions(userTypeGateway, userType, persistence);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeNotFound() {
        var input = createInput(NEW_NAME);

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(ID, input)
        );

        verify(userTypeGateway).findById(anyLong());
        verifyNoMoreInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyInUse() {
        var userType = mock(UserType.class);
        var input = createInput(NEW_NAME);
        var persistence = mock(UserTypePersistenceDTO.class);

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.of(userType));
        when(userType.export()).thenReturn(persistence);
        when(persistence.name()).thenReturn(CURRENT_NAME);
        when(userTypeGateway.existsByName(anyString())).thenReturn(true);

        assertThrows(UserTypeAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(ID, input)
        );

        verify(userTypeGateway).findById(anyLong());
        verify(userType).export();
        verify(userTypeGateway).existsByName(anyString());
        verify(userTypeGateway, never()).update(any());
        verify(userType, never()).update(any());
        verifyNoMoreInteractions(userTypeGateway, userType, persistence);
    }


    private UpdateUserTypeInputDTO createInput(String name) {
        return new UpdateUserTypeInputDTO(name);
    }

    private UserTypeOutputDTO createOutput(String name) {
        return new UserTypeOutputDTO(ID, name);
    }

}