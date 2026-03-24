package br.com.Restaurant.Management.API.menuitems.core.domain;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    @Test
    @DisplayName("Deve criar um novo MenuItem com sucesso")
    void shouldCreateNewMenuItem() {
        var item = MenuItem.newMenuItem(
                "Burger", "Delicious", BigDecimal.valueOf(25.0), true, "img.png", 1L
        );

        assertAll(
                () -> assertNull(item.getId()),
                () -> assertEquals("Burger", item.getName()),
                () -> assertEquals(BigDecimal.valueOf(25.0), item.getPrice()),
                () -> assertTrue(item.getAvailableOnlyInRestaurant()),
                () -> assertEquals(1L, item.getRestaurantId())
        );
    }

    @Test
    @DisplayName("Deve restaurar um MenuItem existente")
    void shouldRestoreMenuItem() {
        var item = MenuItem.restore(
                10L, "Pizza", "Cheesy", BigDecimal.valueOf(50.0), false, null, 2L
        );

        assertEquals(10L, item.getId());
        assertFalse(item.getAvailableOnlyInRestaurant());
    }

    @Test
    @DisplayName("Deve atualizar os campos do MenuItem")
    void shouldUpdateMenuItem() {
        var item = MenuItem.newMenuItem("Old", "Old Desc", BigDecimal.ONE, false, "old.png", 1L);

        item.update("New", "New Desc", BigDecimal.TEN, true, "new.png");

        assertEquals("New", item.getName());
        assertEquals("New Desc", item.getDescription());
        assertEquals(BigDecimal.TEN, item.getPrice());
        assertTrue(item.getAvailableOnlyInRestaurant());
        assertEquals("new.png", item.getImagePath());
    }

    @Test
    @DisplayName("Não deve atualizar campos se os valores forem nulos")
    void shouldNotUpdateWhenFieldsAreNull() {
        var item = MenuItem.newMenuItem("Keep", "Keep", BigDecimal.ONE, false, "keep.png", 1L);

        item.update(null, null, null, null, null);

        assertEquals("Keep", item.getName());
        assertEquals("keep.png", item.getImagePath());
    }

    @Test
    @DisplayName("Deve lançar exceção para nome inválido")
    void shouldThrowExceptionForInvalidName() {
        assertThrows(IllegalArgumentException.class, () ->
                MenuItem.newMenuItem(null, "Desc", BigDecimal.ONE, false, null, 1L));

        assertThrows(IllegalArgumentException.class, () ->
                MenuItem.newMenuItem(" ", "Desc", BigDecimal.ONE, false, null, 1L));
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição inválida")
    void shouldThrowExceptionForInvalidDescription() {
        assertThrows(IllegalArgumentException.class, () ->
                MenuItem.newMenuItem("Name", "", BigDecimal.ONE, false, null, 1L));
    }

    @Test
    @DisplayName("Deve lançar exceção para preço inválido")
    void shouldThrowExceptionForInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () ->
                MenuItem.newMenuItem("Name", "Desc", null, false, null, 1L));

        assertThrows(IllegalArgumentException.class, () ->
                MenuItem.newMenuItem("Name", "Desc", BigDecimal.ZERO, false, null, 1L));

        assertThrows(IllegalArgumentException.class, () ->
                MenuItem.newMenuItem("Name", "Desc", BigDecimal.valueOf(-1), false, null, 1L));
    }

    @Test
    @DisplayName("Deve converter para DTOs de saída e persistência")
    void shouldExportToDtos() {
        var item = MenuItem.restore(1L, "Name", "Desc", BigDecimal.ONE, true, "path", 1L);

        var output = item.toOutput();
        var persistence = item.export();

        assertEquals(item.getId(), output.id());
        assertEquals(item.getName(), persistence.name());
    }

    @Test
    @DisplayName("Deve definir availableOnlyInRestaurant como false se for nulo na criação")
    void shouldDefaultAvailableToFalse() {
        var item = MenuItem.newMenuItem("Name", "Desc", BigDecimal.ONE, null, null, 1L);
        assertFalse(item.getAvailableOnlyInRestaurant());
    }
}