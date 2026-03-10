package br.com.Restaurant.Management.API.users.infra.gateway;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import br.com.Restaurant.Management.API.users.infra.entity.UserEntity;
import br.com.Restaurant.Management.API.users.infra.mapper.UserEntityMapper;
import br.com.Restaurant.Management.API.users.infra.repository.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGatewayImp implements UserGateway {

    private final UserJpaRepository repository;

    public UserGatewayImp(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        UserEntity entity = UserEntityMapper.toEntity(user);
        UserEntity saved = repository.save(entity);
        return UserEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(UserEntityMapper::toDomain);
    }

    @Override
    public PaginatedResponseDTO<User> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<UserEntity> result = repository.findAll(pageable);

        return new PaginatedResponseDTO<>(
                result.getContent().stream().map(UserEntityMapper::toDomain).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public User update(User user) {
        UserEntity entity = UserEntityMapper.toEntity(user);
        UserEntity saved = repository.save(entity);
        return UserEntityMapper.toDomain(saved);
    }
}
