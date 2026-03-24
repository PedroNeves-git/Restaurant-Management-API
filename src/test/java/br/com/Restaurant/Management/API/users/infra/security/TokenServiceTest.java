package br.com.Restaurant.Management.API.users.infra.security;

import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.infra.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class TokenServiceTest {

    private TokenService tokenService;
    private final String SECRET = "minha-senha-secreta-de-teste-123";

    @BeforeEach
    void setup() {
        tokenService = new TokenService();
        setField(tokenService, "secret", SECRET);
    }

    @Test
    @DisplayName("Deve gerar um token JWT válido baseado no login do usuário")
    void shouldGenerateTokenSuccessfully() {
        var user = new UserEntity("carlos.silva", "senha123", UserRole.RESTAURANT_OWNER);

        var token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    @DisplayName("Deve validar um token correto e retornar o login (subject)")
    void shouldValidateTokenAndReturnSubject() {
        var user = new UserEntity("admin.login", "123456", UserRole.RESTAURANT_OWNER);
        var token = tokenService.generateToken(user);

        var subject = tokenService.validateToken(token);

        assertEquals("admin.login", subject);
    }

    @Test
    @DisplayName("Deve retornar string vazia quando o token for inválido")
    void shouldReturnEmptyStringWhenTokenIsInvalid() {
        var tokenInvalido = "token.com.assinatura.errada";

        var subject = tokenService.validateToken(tokenInvalido);

        assertEquals("", subject);
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando houver erro na criação do token")
    void shouldThrowExceptionOnTokenCreationError() {
        setField(tokenService, "secret", null);
        var user = new UserEntity("login", "pass", UserRole.RESTAURANT_OWNER);

        assertThrows(RuntimeException.class, () -> {
            tokenService.generateToken(user);
        });
    }
}