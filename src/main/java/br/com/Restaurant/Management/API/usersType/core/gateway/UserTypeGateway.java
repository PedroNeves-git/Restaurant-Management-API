package br.com.Restaurant.Management.API.usersType.core.gateway;

import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;

import java.util.Optional;

public interface UserTypeGateway {
    UserType create(UserType userType);
    UserType update(UserType userType);
    void deleteById(Long id);
    Optional<UserType> findById(Long id);
    Optional<UserType> findByName(String name);

    boolean existsByName(String name);

    PaginatedResponseDTO<UserType> findAll(int page, int size);
}
