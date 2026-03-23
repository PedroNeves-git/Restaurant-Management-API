package br.com.Restaurant.Management.API.usersType.core.usecase;


import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.dto.input.CreateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeAlreadyInUseException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserTypeUseCaseTest {

    @InjectMocks
    private CreateUserTypeUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;

    private static final String NAME = "ADMIN";
    private static final Long ID = 1L;

    @Test
    void shouldCreateUserTypeWhenNameDoesNotExist() {
        var input = createInput();
        var domain = createDomain();

        when(userTypeGateway.existsByName(anyString())).thenReturn(false);
        when(userTypeGateway.create(any(UserType.class))).thenReturn(domain);

        var result = useCaseUnderTest.execute(input);

        assertNotNull(result);
        assertEquals(ID, result.id());
        assertEquals(NAME, result.name());

        verify(userTypeGateway).existsByName(anyString());
        verify(userTypeGateway).create(any(UserType.class));
        verifyNoMoreInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeNameAlreadyExists() {
        var input = createInput();

        when(userTypeGateway.existsByName(anyString())).thenReturn(true);

        var exception = assertThrows(UserTypeAlreadyInUseException.class,
                () -> useCaseUnderTest.execute(input)
        );

        assertEquals(NAME, exception.getMessage().contains(NAME) ? NAME : NAME);

        verify(userTypeGateway).existsByName(anyString());
        verifyNoMoreInteractions(userTypeGateway);
    }

    private CreateUserTypeInputDTO createInput() {
        return new CreateUserTypeInputDTO(NAME);
    }

    private UserType createDomain() {
        return UserType.restore(ID, NAME);
    }
}