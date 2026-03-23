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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListUserTypeByIdUseCaseTest {

    private final Long ID = 1L;

    @InjectMocks
    private ListUserTypeByIdUseCase listUserTypeByIdUseCase;

    @Mock
    private UserTypeGateway userTypeGateway;


    @Test
    void shouldReturnUserTypeWhenItemExists() {
        var userType = mock(UserType.class);
        var expectedOutput = new UserTypeOutputDTO(ID, "Owner");

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.of(userType));
        when(userType.toOutput()).thenReturn(expectedOutput);

        var result = listUserTypeByIdUseCase.execute(ID);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userTypeGateway).findById(anyLong());
        verify(userType).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> listUserTypeByIdUseCase.execute(ID));

        verify(userTypeGateway).findById(anyLong());
        verifyNoMoreInteractions(userTypeGateway);
    }

}
