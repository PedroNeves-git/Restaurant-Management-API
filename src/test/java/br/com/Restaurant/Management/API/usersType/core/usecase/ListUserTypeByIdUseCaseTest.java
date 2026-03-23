package br.com.Restaurant.Management.API.usersType.core.usecase;

import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUserTypeByIdUseCaseTest {
    private static final Long ID = 1L;
    private static final String NAME = "ADMIN";

    @InjectMocks
    private ListUserTypeByIdUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;


    @Test
    void shouldReturnUserTypeWhenIdExists() {
        var userType = mock(UserType.class);
        var output = createOutput();

        when(userType.toOutput()).thenReturn(output);
        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.of(userType));

        var result = useCaseUnderTest.execute(ID);

        assertNotNull(result);
        assertEquals(ID, result.id());
        assertEquals(NAME, result.name());

        verify(userTypeGateway).findById(anyLong());
        verify(userType).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(ID)
        );

        verify(userTypeGateway).findById(anyLong());
        verifyNoMoreInteractions(userTypeGateway);
    }


    private UserTypeOutputDTO createOutput() {
        return new UserTypeOutputDTO(ID, NAME);
    }

}