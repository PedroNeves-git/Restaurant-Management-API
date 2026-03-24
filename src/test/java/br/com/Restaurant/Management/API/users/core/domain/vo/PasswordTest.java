package br.com.Restaurant.Management.API.users.core.domain.vo;


import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    @DisplayName("Deve criar uma senha válida com todos os requisitos")
    void shouldCreateValidPassword() {
        var password = new Password("  Senha123  ");

        assertEquals("Senha123", password.value());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for null")
    void shouldThrowExceptionWhenNull() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Password(null)
        );
        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for apenas espaços")
    void shouldThrowExceptionWhenBlank() {
        assertThrows(InvalidFieldException.class, () -> new Password("   "));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for muito curta (menor que 8)")
    void shouldThrowExceptionWhenTooShort() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Password("Ab12345")
        );
        assertEquals("TOO_SHORT", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for muito longa (maior que 64)")
    void shouldThrowExceptionWhenTooLong() {
        var longPassword = "A1a" + "a".repeat(62);
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Password(longPassword)
        );
        assertEquals("TOO_LONG", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver letra maiúscula")
    void shouldThrowExceptionWhenNoUppercase() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Password("senha123")
        );
        assertEquals("WEAK_PASSWORD", exception.getCode());
        assertEquals("Password must contain at least one uppercase letter", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver letra minúscula")
    void shouldThrowExceptionWhenNoLowercase() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Password("SENHA123")
        );
        assertEquals("WEAK_PASSWORD", exception.getCode());
        assertEquals("Password must contain at least one lowercase letter", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver número")
    void shouldThrowExceptionWhenNoNumber() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Password("SenhaSenha")
        );
        assertEquals("WEAK_PASSWORD", exception.getCode());
        assertEquals("Password must contain at least one number", exception.getMessage());
    }

    @Test
    @DisplayName("Deve mascarar a senha no método toString")
    void shouldMaskPasswordInToString() {
        var password = new Password("Senha123");
        assertEquals("Password(****)", password.toString());
    }

    @Test
    @DisplayName("Deve cobrir métodos automáticos do Record (equals e hashCode)")
    void testRecordMethods() {
        var p1 = new Password("Senha123");
        var p2 = new Password("Senha123");
        var p3 = new Password("Outra123");

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}