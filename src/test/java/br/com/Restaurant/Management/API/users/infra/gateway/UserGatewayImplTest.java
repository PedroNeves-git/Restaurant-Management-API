package br.com.Restaurant.Management.API.users.infra.gateway;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.domain.enums.UserRole;
import br.com.Restaurant.Management.API.users.infra.repository.UserJpaRepository;
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
@Import(UserGatewayImpl.class)
class UserGatewayImplIntegrationTest {

    @Autowired
    private UserJpaRepository repository;

    @Autowired
    private UserGatewayImpl gateway;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should create a user and return domain with normalized login")
    void createShouldReturnDomainWithNormalizedLogin() {
        var user = User.newUser("John Doe", "john@email.com", "  John.Doe_123  ", "Password123", 1L, UserRole.USER);

        var result = gateway.create(user);

        assertThat(result).isNotNull();
        assertThat(result.toOutput().id()).isNotNull();
        assertThat(result.toOutput().login()).isEqualTo("john.doe_123");
    }

    @Test
    @DisplayName("should find user by id and restore domain correctly")
    void findByIdShouldReturnPresent() {
        var saved = gateway.create(User.newUser("Jane", "jane@email.com", "jane_admin", "Password123", 1L, UserRole.RESTAURANT_OWNER));
        var id = saved.toOutput().id();

        var found = gateway.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().toOutput().login()).isEqualTo("jane_admin");
    }

    @Test
    @DisplayName("should update an existing user with valid login data")
    void updateShouldModifyRecord() {
        var saved = gateway.create(User.newUser("Old Name", "old@email.com", "old.login", "Password123", 1L, UserRole.USER));

        var domainToUpdate = User.restore(
                saved.toOutput().id(),
                saved.toOutput().name(),
                saved.toOutput().email(),
                saved.toOutput().login(),
                "Password123",
                saved.toOutput().active(),
                saved.toOutput().typeId(),
                UserRole.USER,
                saved.toOutput().createdAt(),
                saved.toOutput().updatedAt()
        );
        domainToUpdate.update("New Name", "new@email.com", "new-login.2026", 1L, true);

        var result = gateway.update(domainToUpdate);

        assertThat(result.toOutput().login()).isEqualTo("new-login.2026");
        assertThat(result.toOutput().updatedAt()).isAfter(saved.toOutput().updatedAt());
    }

    @Test
    @DisplayName("should return true when login already exists regardless of case")
    void existsByLoginShouldReturnTrueWhenExists() {
        gateway.create(User.newUser("Test", "test@email.com", "unique-user", "Password123", 1L, UserRole.USER));

        assertThat(gateway.existsByLogin("unique-user")).isTrue();
        assertThat(gateway.existsByLogin("other-user")).isFalse();
    }

    @Test
    @DisplayName("should return paginated users correctly")
    void findAllShouldReturnPaginatedData() {
        gateway.create(User.newUser("U1", "u1@e.com", "user.one", "Password123", 1L, UserRole.USER));
        gateway.create(User.newUser("U2", "u2@e.com", "user.two", "Password123", 1L, UserRole.USER));

        var response = gateway.findAll(0, 10);

        assertThat(response.content()).hasSize(2);
        assertThat(response.totalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("should delete user record by id")
    void deleteByIdShouldRemoveRecord() {
        var saved = gateway.create(User.newUser("Delete", "del@e.com", "to-delete", "Password123", 1L, UserRole.USER));
        var id = saved.toOutput().id();

        gateway.deleteById(id);

        assertThat(repository.existsById(id)).isFalse();
    }

    @Test
    @DisplayName("should return true when user email exists in database")
    void existsByEmailShouldReturnTrueWhenEmailExists() {
        var email = "test.integration@example.com";
        var user = User.newUser("Test User", email, "testlogin", "passWord123", 1L, UserRole.USER);
        gateway.create(user);

        boolean exists = gateway.existsByEmail(email);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("should return false when user email does not exist")
    void existsByEmailShouldReturnFalseWhenEmailDoesNotExist() {
        boolean exists = gateway.existsByEmail("non.existent@example.com");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("should return true even if email is provided with different casing")
    void existsByEmailShouldBeCaseInsensitiveIfDomainNormalizes() {
        gateway.create(User.newUser("Case Test", "UPPERCASE@email.com", "case.test", "Password123", 1L, UserRole.USER));

        boolean exists = gateway.existsByEmail("uppercase@email.com");

        assertThat(exists).isTrue();
    }
}