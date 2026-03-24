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
public class ListUserTypeByIdUseCaseTest {

    private static final Long ID = 1L;
    private static final String NAME = "Admin";

    @InjectMocks
    private ListUserTypeByIdUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;


    @Test
    void shouldReturnUserTypeWhenItExists() {
        var userType = mock(UserType.class);
        var expectedOutput = createOutput();

        when(userTypeGateway.findById(ID)).thenReturn(Optional.of(userType));
        when(userType.toOutput()).thenReturn(expectedOutput);

        var result = useCaseUnderTest.execute(ID);

        assertNotNull(result);
        assertEquals(expectedOutput, result);

        verify(userTypeGateway).findById(ID);
        verify(userType).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        when(userTypeGateway.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCaseUnderTest.execute(ID));

        verify(userTypeGateway).findById(ID);
        verifyNoMoreInteractions(userTypeGateway);
    }


    private UserTypeOutputDTO createOutput() {
        return new UserTypeOutputDTO(
                ID,
                NAME
        );
    }
}
