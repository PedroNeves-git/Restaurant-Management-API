package br.com.Restaurant.Management.API.users.core.domain.vo;

import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("Deve criar um email válido e normalizar (trim e lowercase)")
    void shouldCreateValidEmail() {
        var email = new Email("  CONTATO@claro.com.br  ");

        assertEquals("contato@claro.com.br", email.value());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o email for null")
    void shouldThrowExceptionWhenNull() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Email(null)
        );

        assertEquals("email", exception.getField());
        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o email for vazio ou apenas espaços")
    void shouldThrowExceptionWhenBlank() {
        assertThrows(InvalidFieldException.class, () -> new Email(""));
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Email("   ")
        );

        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o formato do email for inválido")
    void shouldThrowExceptionWhenInvalidFormat() {
        assertThrows(InvalidFieldException.class, () -> new Email("emailinvalido.com"));
        assertThrows(InvalidFieldException.class, () -> new Email("@claro.com.br"));

        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Email("teste@")
        );

        assertEquals("INVALID_FORMAT", exception.getCode());
    }

    @Test
    @DisplayName("Deve cobrir métodos automáticos do Record (equals e hashCode)")
    void testRecordMethods() {
        var email1 = new Email("teste@teste.com");
        var email2 = new Email("teste@teste.com");
        var email3 = new Email("outro@teste.com");

        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
        assertEquals(email1.hashCode(), email2.hashCode());
        assertNotNull(email1.toString());
    }
}
