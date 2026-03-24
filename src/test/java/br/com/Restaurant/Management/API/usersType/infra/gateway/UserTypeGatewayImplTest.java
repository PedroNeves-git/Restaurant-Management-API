package br.com.Restaurant.Management.API.usersType.infra.gateway;

import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.infra.repository.UserTypeJpaRepository;
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
@Import(UserTypeGatewayImpl.class)
class UserTypeGatewayImplIntegrationTest {

    @Autowired
    private UserTypeJpaRepository repository;

    @Autowired
    private UserTypeGatewayImpl gateway;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should create a UserType and return domain with generated id")
    void createShouldReturnDomainWithId() {
        var domain = UserType.newUserType("Administrator");

        var result = gateway.create(domain);

        assertThat(result).isNotNull();
        assertThat(result.toOutput().id()).isNotNull();
        assertThat(result.toOutput().name()).isEqualTo("Administrator");
    }

    @Test
    @DisplayName("should find UserType by id when it exists")
    void findByIdShouldReturnPresent() {
        var saved = gateway.create(UserType.newUserType("Waiter"));
        var id = saved.toOutput().id();

        var found = gateway.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().toOutput().name()).isEqualTo("Waiter");
    }

    @Test
    @DisplayName("should update an existing UserType using restore and update logic")
    void updateShouldModifyRecord() {
        var saved = gateway.create(UserType.newUserType("Manager"));
        var domainToUpdate = UserType.restore(saved.toOutput().id(), saved.toOutput().name());
        domainToUpdate.update("Senior Manager");

        var result = gateway.update(domainToUpdate);

        assertThat(result.toOutput().name()).isEqualTo("Senior Manager");
        assertThat(result.toOutput().id()).isEqualTo(saved.toOutput().id());
    }

    @Test
    @DisplayName("should return true when user type name already exists")
    void existsByNameShouldReturnTrueWhenExists() {
        gateway.create(UserType.newUserType("Chef"));

        boolean exists = gateway.existsByName("Chef");
        boolean notExists = gateway.existsByName("Hostess");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("should find UserType by name directly")
    void findByNameShouldReturnResult() {
        gateway.create(UserType.newUserType("Customer"));

        var found = gateway.findByName("Customer");

        assertThat(found).isPresent();
    }

    @Test
    @DisplayName("should return paginated results for all user types")
    void findAllShouldReturnPaginatedData() {
        gateway.create(UserType.newUserType("Type 1"));
        gateway.create(UserType.newUserType("Type 2"));
        gateway.create(UserType.newUserType("Type 3"));

        var response = gateway.findAll(0, 2);

        assertThat(response.content()).hasSize(2);
        assertThat(response.totalElements()).isEqualTo(3);
        assertThat(response.totalPages()).isEqualTo(2);
    }

    @Test
    @DisplayName("should delete user type record by id")
    void deleteByIdShouldRemoveFromDatabase() {
        var saved = gateway.create(UserType.newUserType("Temporary"));
        var id = saved.toOutput().id();

        gateway.deleteById(id);

        assertThat(repository.existsById(id)).isFalse();
    }
}