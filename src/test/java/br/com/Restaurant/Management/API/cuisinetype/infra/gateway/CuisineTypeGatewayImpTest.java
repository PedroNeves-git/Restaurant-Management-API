package br.com.Restaurant.Management.API.cuisinetype.infra.gateway;

import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.infra.repository.CuisineTypeJpaRepository;
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
@Import(CuisineTypeGatewayImp.class)
class CuisineTypeGatewayImpIntegrationTest {

    @Autowired
    private CuisineTypeJpaRepository repository;

    @Autowired
    private CuisineTypeGatewayImp gateway;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should create a CuisineType and return domain with generated id")
    void createShouldReturnDomainWithId() {
        var domain = CuisineType.newCuisineType("Italian");

        var result = gateway.create(domain);

        assertThat(result).isNotNull();
        assertThat(result.toOutput().id()).isNotNull();
        assertThat(result.toOutput().name()).isEqualTo("Italian");
    }

    @Test
    @DisplayName("should find CuisineType by id when it exists")
    void findByIdShouldReturnPresent() {
        var created = gateway.create(CuisineType.newCuisineType("Mexican"));
        var id = created.toOutput().id();

       var found = gateway.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().toOutput().name()).isEqualTo("Mexican");
    }

    @Test
    @DisplayName("should return empty when finding by non-existent id")
    void findByIdShouldReturnEmpty() {
        var found = gateway.findById(-1L);

        assertThat(found).isNotPresent();
    }

    @Test
    @DisplayName("should delete CuisineType by id")
    void deleteByIdShouldRemoveRecord() {
        var created = gateway.create(CuisineType.newCuisineType("Thai"));
        var id = created.toOutput().id();

        gateway.deleteById(id);

        var after = gateway.findById(id);
        assertThat(after).isNotPresent();
    }

    @Test
    @DisplayName("should return true when CuisineType name already exists")
    void existsByNameShouldBeTrueWhenExists() {
        gateway.create(CuisineType.newCuisineType("Indian"));

        boolean exists = gateway.existsByName("Indian");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("should return paginated results correctly")
    void findAllShouldReturnPaginatedResponse() {
        gateway.create(CuisineType.newCuisineType("French"));
        gateway.create(CuisineType.newCuisineType("Japanese"));
        gateway.create(CuisineType.newCuisineType("Korean"));

        var page = gateway.findAll(0, 2);

        assertThat(page).isNotNull();
        assertThat(page.content()).hasSize(2);
        assertThat(page.totalElements()).isEqualTo(3);
        assertThat(page.page()).isEqualTo(0);
    }
}