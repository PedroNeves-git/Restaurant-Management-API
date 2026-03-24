package br.com.Restaurant.Management.API.users.core.domain.vo;

import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    @DisplayName("Deve criar um login válido e normalizar (trim e lowercase)")
    void shouldCreateValidLogin() {
        var login = new Login("  User.Test_01  ");

        assertEquals("user.test_01", login.value());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login for null")
    void shouldThrowExceptionWhenNull() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Login(null)
        );

        assertEquals("login", exception.getField());
        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login for vazio ou apenas espaços")
    void shouldThrowExceptionWhenBlank() {
        assertThrows(InvalidFieldException.class, () -> new Login(""));

        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Login("   ")
        );

        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tamanho for inválido (menor que 3 ou maior que 30)")
    void shouldThrowExceptionWhenInvalidLength() {
        assertThrows(InvalidFieldException.class, () -> new Login("ab"));

        var longLogin = "a".repeat(31);
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Login(longLogin)
        );

        assertEquals("INVALID_LENGTH", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login contiver caracteres inválidos")
    void shouldThrowExceptionWhenInvalidFormat() {
        assertThrows(InvalidFieldException.class, () -> new Login("user name"));
        assertThrows(InvalidFieldException.class, () -> new Login("user@123"));

        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Login("admin#")
        );

        assertEquals("INVALID_FORMAT", exception.getCode());
    }

    @Test
    @DisplayName("Deve cobrir métodos automáticos do Record (equals, hashCode e toString)")
    void testRecordMethods() {
        var login1 = new Login("admin.test");
        var login2 = new Login("admin.test");
        var login3 = new Login("other.user");

        assertEquals(login1, login2);
        assertNotEquals(login1, login3);
        assertEquals(login1.hashCode(), login2.hashCode());
        assertNotNull(login1.toString());
    }
}