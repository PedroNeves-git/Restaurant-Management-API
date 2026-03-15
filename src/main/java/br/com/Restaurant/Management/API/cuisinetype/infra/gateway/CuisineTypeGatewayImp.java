package br.com.Restaurant.Management.API.cuisinetype.infra.gateway;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import br.com.Restaurant.Management.API.cuisinetype.infra.entity.CuisineTypeEntity;
import br.com.Restaurant.Management.API.cuisinetype.infra.mapper.CuisineTypeEntityMapper;
import br.com.Restaurant.Management.API.cuisinetype.infra.repository.CuisineTypeJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CuisineTypeGatewayImp implements CuisineTypeGateway {

    private final CuisineTypeJpaRepository repository;

    public CuisineTypeGatewayImp(CuisineTypeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public CuisineType create(CuisineType cuisineType){
        CuisineTypeEntity entity = CuisineTypeEntityMapper.toEntity(cuisineType);
        CuisineTypeEntity saved = repository.save(entity);
        return CuisineTypeEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id){ repository.deleteById(id);}

    @Override
    public Optional<CuisineType> findById(Long id){
        return repository.findById(id).map(CuisineTypeEntityMapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public PaginatedResponseDTO<CuisineType> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CuisineTypeEntity> result = repository.findAll(pageRequest);

        return new PaginatedResponseDTO<>(
                result.getContent().stream().map(CuisineTypeEntityMapper::toDomain).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

}
