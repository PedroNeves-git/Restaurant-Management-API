package br.com.Restaurant.Management.API.restaurant.infra.gateway;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.restaurant.core.domain.Restaurant;
import br.com.Restaurant.Management.API.restaurant.core.gateway.RestaurantGateway;
import br.com.Restaurant.Management.API.restaurant.infra.entity.RestaurantEntity;
import br.com.Restaurant.Management.API.restaurant.infra.mapper.RestaurantEntityMapper;
import br.com.Restaurant.Management.API.restaurant.infra.repository.RestaurantJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RestaurantGatewayImp implements RestaurantGateway {

    private final RestaurantJpaRepository repository;

    public RestaurantGatewayImp(RestaurantJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        return save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        return save(restaurant);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public List<Restaurant> findRestaurantsByRestaurantOwnerId(Long restaurantOwnerId) {
        return repository.findRestaurantsByRestaurantOwnerId(restaurantOwnerId)
                .stream()
                .map(RestaurantEntityMapper::toDomain)
                .toList();
    }

    @Override
    public PaginatedResponseDTO<Restaurant> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<RestaurantEntity> result = repository.findAll(pageable);

        return new PaginatedResponseDTO<>(
                result.getContent().stream().map(RestaurantEntityMapper::toDomain).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return repository.findById(id).map(RestaurantEntityMapper::toDomain);
    }

    private Restaurant save(Restaurant restaurant) {
        return RestaurantEntityMapper.toDomain(
                repository.save(RestaurantEntityMapper.toEntity(restaurant))
        );
    }

}
