package br.com.Restaurant.Management.API.restaurant.infra.gateway;


import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.infra.repository.RestaurantJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(RestaurantGatewayImpl.class)
class RestaurantGatewayImplTest {
    @Autowired
    private RestaurantJpaRepository repository;

    @Autowired
    private RestaurantGatewayImpl gateway;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should create a restaurant and return domain with generated id")
    void createShouldReturnDomainWithId() {
        var restaurant = Restaurant.newRestaurant(
                "Gourmet Garden",
                "Street A, 123",
                "08:00",
                "22:00",
                1L,
                10L
        );

        var result = gateway.create(restaurant);

        assertThat(result).isNotNull();
        assertThat(result.toOutput().id()).isNotNull();
        assertThat(result.toOutput().name()).isEqualTo("Gourmet Garden");
        assertThat(result.toOutput().address()).isEqualTo("Street A, 123");
    }

    @Test
    @DisplayName("should find restaurant by id when it exists")
    void findByIdShouldReturnPresent() {
        var saved = gateway.create(Restaurant.newRestaurant(
                "Sushi Central", "Av. B, 456", "18:00", "23:59", 2L, 11L
        ));
        var id = saved.toOutput().id();

        var found = gateway.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().toOutput().name()).isEqualTo("Sushi Central");
        assertThat(found.get().toOutput().openingTime().toString()).isEqualTo("18:00");
    }

    @Test
    @DisplayName("should update an existing restaurant using restore and update logic")
    void updateShouldModifyRecord() {
        var saved = gateway.create(Restaurant.newRestaurant(
                "Pizza Place", "Street C", "10:00", "22:00", 1L, 1L
        ));

        var domainToUpdate = Restaurant.restore(
                saved.toOutput().id(),
                saved.toOutput().name(),
                saved.toOutput().address(),
                saved.toOutput().openingTime().toString(),
                saved.toOutput().closingTime().toString(),
                saved.toOutput().cuisineTypeId(),
                saved.toOutput().restaurantOwnerId()
        );

        domainToUpdate.update("Pizza Place Gourmet", "New Address 99", "11:00", "23:00", 1L);

        var result = gateway.update(domainToUpdate);

        assertThat(result.toOutput().name()).isEqualTo("Pizza Place Gourmet");
        assertThat(result.toOutput().address()).isEqualTo("New Address 99");
        assertThat(result.toOutput().id()).isEqualTo(saved.toOutput().id());
    }

    @Test
    @DisplayName("should find all restaurants belonging to a specific owner")
    void findByOwnerIdShouldReturnFilteredList() {
        var ownerId = 50L;
        gateway.create(Restaurant.newRestaurant("Rest 1", "Addr 1", "08:00", "20:00", 1L, ownerId));
        gateway.create(Restaurant.newRestaurant("Rest 2", "Addr 2", "09:00", "21:00", 1L, ownerId));
        gateway.create(Restaurant.newRestaurant("Rest 3", "Addr 3", "10:00", "22:00", 1L, 99L));

        var results = gateway.findRestaurantsByRestaurantOwnerId(ownerId);

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(r -> r.toOutput().restaurantOwnerId().equals(ownerId));
    }

    @Test
    @DisplayName("should return paginated results correctly")
    void findAllShouldReturnPaginatedResponse() {
        gateway.create(Restaurant.newRestaurant("R1", "Adress A1", "08:00", "18:00", 1L, 1L));
        gateway.create(Restaurant.newRestaurant("R2", "Adress A2", "08:00", "18:00", 1L, 1L));
        gateway.create(Restaurant.newRestaurant("R3", "Adress A3", "08:00", "18:00", 1L, 1L));

        var response = gateway.findAll(0, 2);

        assertThat(response.content()).hasSize(2);
        assertThat(response.totalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("should delete restaurant record by id")
    void deleteByIdShouldRemoveFromDatabase() {
        var saved = gateway.create(Restaurant.newRestaurant("Temp",
                "AddrESS a1",
                "00:00",
                "01:00",
                1L,
                1L));
        var id = saved.toOutput().id();

        gateway.deleteById(id);

        assertThat(repository.existsById(id)).isFalse();
    }

    @Test
    @DisplayName("should return true when restaurant name exists")
    void existsByNameShouldReturnTrueWhenNameExists() {
        gateway.create(Restaurant.newRestaurant("Existing Name", "Address 01", "08:00", "20:00", 1L, 1L));

        boolean exists = gateway.existsByName("Existing Name");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("should return false when restaurant name does not exist")
    void existsByNameShouldReturnFalseWhenNameDoesNotExist() {
        boolean exists = gateway.existsByName("Non Existent");

        assertThat(exists).isFalse();
    }
}
