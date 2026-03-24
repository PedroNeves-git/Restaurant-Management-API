package br.com.Restaurant.Management.API.usersType.core.usecase;


import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUsersTypeUseCaseTest {
    private static final int PAGE = 0;
    private static final int SIZE = 10;
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String NAME_1 = "ADMIN";
    private static final String NAME_2 = "CUSTOMER";


    @InjectMocks
    private ListUsersTypeUseCase useCaseUnderTest;

    @Mock
    private UserTypeGateway userTypeGateway;



    @Test
    void shouldReturnPaginatedUserTypes() {
        var userType1 = mock(UserType.class);
        var userType2 = mock(UserType.class);

        var output1 = createOutput(ID_1, NAME_1);
        var output2 = createOutput(ID_2, NAME_2);

        when(userType1.toOutput()).thenReturn(output1);
        when(userType2.toOutput()).thenReturn(output2);

        var gatewayResponse = createGatewayResponse(
                List.of(userType1, userType2),
                2,
                1
        );

        when(userTypeGateway.findAll(anyInt(), anyInt())).thenReturn(gatewayResponse);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(output1, result.content().get(0));
        assertEquals(output2, result.content().get(1));
        assertEquals(PAGE, result.page());
        assertEquals(SIZE, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());

        verify(userTypeGateway).findAll(anyInt(), anyInt());
        verify(userType1).toOutput();
        verify(userType2).toOutput();
        verifyNoMoreInteractions(userTypeGateway, userType1, userType2);
    }

    @Test
    void shouldReturnEmptyListWhenNoUserTypesExist() {
        var gatewayResponse = createGatewayResponse(
                List.of(),
                0,
                0
        );

        when(userTypeGateway.findAll(anyInt(), anyInt())).thenReturn(gatewayResponse);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(userTypeGateway).findAll(anyInt(), anyInt());
        verifyNoMoreInteractions(userTypeGateway);
    }



    private UserTypeOutputDTO createOutput(Long id, String name) {
        return new UserTypeOutputDTO(id, name);
    }

    private PaginatedResponseDTO<UserType> createGatewayResponse(List<UserType> content,
                                                                 long totalElements,
                                                                 int totalPages) {
        return new PaginatedResponseDTO<>(
                content,
                PAGE,
                SIZE,
                totalElements,
                totalPages
        );
    }
}