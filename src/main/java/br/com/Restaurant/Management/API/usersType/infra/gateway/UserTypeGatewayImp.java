package br.com.Restaurant.Management.API.usersType.infra.gateway;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import br.com.Restaurant.Management.API.usersType.infra.entity.UserTypeEntity;
import br.com.Restaurant.Management.API.usersType.infra.mapper.UserTypeEntityMapper;
import br.com.Restaurant.Management.API.usersType.infra.repository.UserTypeJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserTypeGatewayImp implements UserTypeGateway {

    private final UserTypeJpaRepository repository;

    public UserTypeGatewayImp(UserTypeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserType create(UserType userType){
        UserTypeEntity entity = UserTypeEntityMapper.toEntity(userType);
        UserTypeEntity saved = repository.save(entity);
        return UserTypeEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id){ repository.deleteById(id);}

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Optional<UserType> findById(Long id){
        return repository.findById(id).map(UserTypeEntityMapper::toDomain);
    }

    @Override
    public UserType update(UserType userType){
        UserTypeEntity entity = UserTypeEntityMapper.toEntity(userType);
        UserTypeEntity saved = repository.save(entity);
        return UserTypeEntityMapper.toDomain(saved);
    }

    @Override
    public PaginatedResponseDTO<UserType> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserTypeEntity> result = repository.findAll(pageRequest);

        return new PaginatedResponseDTO<>(
                result.getContent().stream().map(UserTypeEntityMapper::toDomain).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public Optional<UserType> findByName(String name){
        return repository.findByName(name);
    }
}
