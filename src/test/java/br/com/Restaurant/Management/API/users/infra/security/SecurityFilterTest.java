package br.com.Restaurant.Management.API.users.infra.security;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.infra.entity.UserEntity;
import br.com.Restaurant.Management.API.users.infra.repository.UserJpaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserJpaRepository userRepository;

    @InjectMocks
    private SecurityFilter securityFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should authenticate user when token is valid and user exists")
    void shouldAuthenticateWhenTokenIsValid() throws ServletException, IOException {
        var token = "valid-token";
        var login = "carlos.silva";
        var user = new UserEntity(login, "password", UserRole.RESTAURANT_OWNER);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.validateToken(token)).thenReturn(login);
        when(userRepository.findByLogin(login)).thenReturn(user);

        securityFilter.doFilterInternal(request, response, filterChain);

        var auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth, "Authentication should not be null");
        assertEquals(login, ((UserEntity) auth.getPrincipal()).getLogin());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate and just continue chain when token is missing")
    void shouldContinueChainWhenTokenIsMissing() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(tokenService);
    }

    @Test
    @DisplayName("Should not authenticate when token validation returns empty")
    void shouldNotAuthenticateWhenTokenIsInvalid() throws ServletException, IOException {
        var token = "invalid-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.validateToken(token)).thenReturn("");

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate when user is not found in database")
    void shouldNotAuthenticateWhenUserNotFound() throws ServletException, IOException {
        var token = "valid-token";
        var login = "non.existent";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.validateToken(token)).thenReturn(login);
        when(userRepository.findByLogin(login)).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}