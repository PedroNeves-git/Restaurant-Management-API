package br.com.Restaurant.Management.API.restaurant.core.domain.vo;

import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    @DisplayName("Deve criar um endereço válido e normalizar com trim")
    void shouldCreateValidAddress() {
        var rawAddress = "  Rua das Flores, 123  ";
        var address = new Address(rawAddress);

        assertEquals("Rua das Flores, 123", address.value());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço for null")
    void shouldThrowExceptionWhenNull() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Address(null)
        );

        assertEquals("address", exception.getField());
        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço for vazio ou apenas espaços")
    void shouldThrowExceptionWhenBlank() {
        assertThrows(InvalidFieldException.class, () -> new Address(""));

        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Address("   ")
        );

        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço for muito curto (menor que 5)")
    void shouldThrowExceptionWhenTooShort() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Address("Rua")
        );

        assertEquals("INVALID_FORMAT", exception.getCode());
        assertEquals("Address is too short", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço for muito longo (maior que 255)")
    void shouldThrowExceptionWhenTooLong() {
        var longAddress = "a".repeat(256);

        var exception = assertThrows(
                InvalidFieldException.class,
                () -> new Address(longAddress)
        );

        assertEquals("INVALID_FORMAT", exception.getCode());
        assertEquals("Address is too long", exception.getMessage());
    }

    @Test
    @DisplayName("Deve cobrir métodos automáticos do Record (equals, hashCode e toString)")
    void testRecordMethods() {
        var addr1 = new Address("Avenida Central, 500");
        var addr2 = new Address("Avenida Central, 500");
        var addr3 = new Address("Outro Endereço, 999");

        assertEquals(addr1, addr2);
        assertNotEquals(addr1, addr3);
        assertEquals(addr1.hashCode(), addr2.hashCode());
        assertNotNull(addr1.toString());
    }
}