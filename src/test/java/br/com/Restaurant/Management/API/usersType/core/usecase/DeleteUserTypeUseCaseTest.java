package br.com.Restaurant.Management.API.usersType.core.usecase;

import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserTypeUseCaseTest {
    private static final Long ID = 1L;
    private static final String NAME = "ADMIN";
    @InjectMocks
    private DeleteUserTypeUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;


    @Test
    void shouldDeleteUserTypeWhenIdExists() {
        var domain = createDomain();

        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.of(domain));

        useCaseUnderTest.execute(ID);

        verify(userTypeGateway).findById(anyLong());
        verify(userTypeGateway).deleteById(anyLong());
        verifyNoMoreInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                    () -> useCaseUnderTest.execute(ID)
        );

        verify(userTypeGateway).findById(anyLong());
        verify(userTypeGateway, never()).deleteById(anyLong());
        verifyNoMoreInteractions(userTypeGateway);
    }


    private UserType createDomain() {
        return UserType.restore(ID, NAME);
    }

}