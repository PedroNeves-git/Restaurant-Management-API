package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListUsersUseCaseTest {

    private static final int PAGE = 0;
    private static final int SIZE = 10;
    private static final long TOTAL_ELEMENTS = 2L;
    private static final int TOTAL_PAGES = 1;
    private static final Long USER_TYPE_ID = 1L;
    private static final String EMAIL = "john@email.com";
    private static final String LOGIN = "johndoe";

    @InjectMocks
    private ListUsersUseCase useCaseUnderTest;

    @Mock
    private UserGateway userGateway;


    @Test
    void shouldReturnPaginatedUsers() {
        var user1 = mock(User.class);
        var user2 = mock(User.class);
        var output1 = createOutput(1L, "John Doe");
        var output2 = createOutput(2L, "Jane Doe");

        var paginatedUsers = new PaginatedResponseDTO<>(
                List.of(user1, user2),
                PAGE,
                SIZE,
                TOTAL_ELEMENTS,
                TOTAL_PAGES
        );

        when(userGateway.findAll(PAGE, SIZE)).thenReturn(paginatedUsers);
        when(user1.toOutput()).thenReturn(output1);
        when(user2.toOutput()).thenReturn(output2);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(List.of(output1, output2), result.content());
        assertEquals(PAGE, result.page());
        assertEquals(SIZE, result.size());
        assertEquals(TOTAL_ELEMENTS, result.totalElements());
        assertEquals(TOTAL_PAGES, result.totalPages());

        verify(userGateway).findAll(PAGE, SIZE);
        verify(user1).toOutput();
        verify(user2).toOutput();
        verifyNoMoreInteractions(userGateway, user1, user2);
    }

    @Test
    void shouldReturnEmptyPaginatedResponseWhenNoUsersExist() {
        var emptyPaginated = new PaginatedResponseDTO<User>(
                Collections.emptyList(),
                PAGE,
                SIZE,
                0L,
                0
        );

        when(userGateway.findAll(PAGE, SIZE)).thenReturn(emptyPaginated);

        var result = useCaseUnderTest.execute(PAGE, SIZE);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(PAGE, result.page());
        assertEquals(SIZE, result.size());
        assertEquals(0L, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(userGateway).findAll(PAGE, SIZE);
        verifyNoMoreInteractions(userGateway);
    }


    private UserOutputDTO createOutput(Long id, String name) {
        return new UserOutputDTO(
                id,
                name,
                EMAIL,
                LOGIN,
                true,
                USER_TYPE_ID,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}