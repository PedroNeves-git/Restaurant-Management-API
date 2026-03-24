package br.com.Restaurant.Management.API.menuitems.infra.gateway;

import br.com.Restaurant.Management.API.menuItems.core.domain.MenuItem;
import br.com.Restaurant.Management.API.menuItems.infra.gateway.MenuItemGatewayImpl;
import br.com.Restaurant.Management.API.menuItems.infra.repository.MenuItemJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(MenuItemGatewayImpl.class)
class MenuItemGatewayImplTest {

    @Autowired
    private MenuItemJpaRepository repository;

    @Autowired
    private MenuItemGatewayImpl gateway;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should create a MenuItem and return domain with generated id")
    void createShouldReturnDomainWithId() {
        var domain = gateway.create(MenuItem.newMenuItem("Pizza Margherita", "Delicious pizza", BigDecimal.valueOf(35.0), true, "", 1L));

        var result = gateway.create(domain);

        assertThat(result).isNotNull();
        assertThat(result.toOutput().id()).isNotNull();
        assertThat(result.toOutput().name()).isEqualTo("Pizza Margherita");
    }

    @Test
    @DisplayName("should find MenuItem by id when it exists")
    void findByIdShouldReturnPresent() {
        var saved = gateway.create(MenuItem.newMenuItem("Burger", "Beef burger", BigDecimal.valueOf(35.0), true, "", 1L));
        var id = saved.toOutput().id();

        var found = gateway.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().toOutput().name()).isEqualTo("Burger");
    }

    @Test
    @DisplayName("should update an existing MenuItem")
    void updateShouldModifyRecord() {
        var saved = gateway.create(MenuItem.newMenuItem("Pasta", "Old description", BigDecimal.valueOf(30.0), true, "", 1L));

        var updatedDomain = MenuItem.restore(
                saved.toOutput().id(),
                "Pasta Carbonara",
                "Updated description",
                BigDecimal.valueOf(38.8),
                true,
                "/image.png",
                1L
        );

        var result = gateway.update(updatedDomain);

        assertThat(result.toOutput().name()).isEqualTo("Pasta Carbonara");
        assertThat(result.toOutput().price()).isEqualByComparingTo(BigDecimal.valueOf(38.8));
    }

    @Test
    @DisplayName("should find all menu items by restaurant id with pagination")
    void findAllByRestaurantIdShouldReturnPaginatedData() {
        var restaurantId = 10L;

        gateway.create(MenuItem.newMenuItem("Item 1", "Desc", BigDecimal.valueOf(10.0), true, "", restaurantId));
        gateway.create(MenuItem.newMenuItem("Item 2", "Desc", BigDecimal.valueOf(20.0), true, "", restaurantId));
        gateway.create(MenuItem.newMenuItem("Item 3", "Desc", BigDecimal.valueOf(30.0), true, "",5L));

        var response = gateway.findAllByRestaurantId(restaurantId, 0, 10);

        assertThat(response.content()).hasSize(2);
        assertThat(response.totalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("should delete MenuItem by id")
    void deleteByIdShouldRemoveRecord() {
        var saved = gateway.create(MenuItem.newMenuItem("Ice Cream", "Dessert", BigDecimal.valueOf(15.0), true, "", 1L));
        var id = saved.toOutput().id();

        gateway.deleteById(id);

        assertThat(repository.existsById(id)).isFalse();
    }


    @Test
    @DisplayName("should find all MenuItems with pagination")
    void findAllShouldReturnPaginatedData() {
        gateway.create(MenuItem.newMenuItem("Pizza", "Desc", BigDecimal.valueOf(40.0), true, "", 1L));
        gateway.create(MenuItem.newMenuItem("Burger", "Desc", BigDecimal.valueOf(30.0), true, "", 1L));
        gateway.create(MenuItem.newMenuItem("Sushi", "Desc", BigDecimal.valueOf(60.0), true, "", 2L));

        int page = 0;
        int size = 2;
        var response = gateway.findAll(page, size);

        assertThat(response).isNotNull();
        assertThat(response.content()).hasSize(2);
        assertThat(response.totalElements()).isEqualTo(3);
        assertThat(response.totalPages()).isEqualTo(2);
        assertThat(response.page()).isEqualTo(0);

        assertThat(response.content().get(0)).isInstanceOf(MenuItem.class);
    }

    @Test
    @DisplayName("should return empty page when no items exist")
    void findAllShouldReturnEmptyPageWhenNoData() {
        var response = gateway.findAll(0, 10);

        assertThat(response.content()).isEmpty();
        assertThat(response.totalElements()).isZero();
        assertThat(response.totalPages()).isZero();
    }
}