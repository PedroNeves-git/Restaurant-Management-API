package br.com.Restaurant.Management.API.restaurant.core.domain.vo;

import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTimeTest {

    @Test
    @DisplayName("Deve criar horário de abertura válido")
    void shouldCreateOpeningTime() {
        var time = BusinessTime.openingTime("08:00");

        assertEquals(LocalTime.of(8, 0), time.value());
    }

    @Test
    @DisplayName("Deve criar horário de fechamento válido")
    void shouldCreateClosingTime() {
        var time = BusinessTime.closingTime("22:30");

        assertEquals(LocalTime.of(22, 30), time.value());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o valor for null")
    void shouldThrowExceptionWhenNull() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> BusinessTime.openingTime(null)
        );

        assertEquals("openingTime", exception.getField());
        assertEquals("REQUIRED", exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o valor for vazio ou espaços")
    void shouldThrowExceptionWhenBlank() {
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> BusinessTime.closingTime("   ")
        );

        assertEquals("closingTime", exception.getField());
        assertEquals("Closing time is required", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o formato do horário for inválido")
    void shouldThrowExceptionWhenInvalidFormat() {
        assertThrows(InvalidFieldException.class, () -> BusinessTime.openingTime("0800"));
        assertThrows(InvalidFieldException.class, () -> BusinessTime.openingTime("AB:CD"));
        var exception = assertThrows(
                InvalidFieldException.class,
                () -> BusinessTime.openingTime("25:00")
        );

        assertEquals("INVALID_FORMAT", exception.getCode());
        assertEquals("Invalid time format, expected HH:mm", exception.getMessage());
    }

    @Test
    @DisplayName("Deve cobrir métodos automáticos do Record e igualdade")
    void testRecordMethods() {
        var t1 = BusinessTime.openingTime("10:00");
        var t2 = BusinessTime.openingTime("10:00");
        var t3 = BusinessTime.closingTime("10:00");

        assertEquals(t1, t2);
        assertEquals(t1, t3);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotNull(t1.toString());
    }
}