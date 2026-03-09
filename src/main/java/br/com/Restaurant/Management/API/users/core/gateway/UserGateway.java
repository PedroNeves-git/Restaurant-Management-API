package br.com.Restaurant.Management.API.users.core.gateway;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.common.PaginatedResponseDTO;

import java.util.Optional;

public interface UserGateway {
    User create(User user);
    User update(User user);
    Optional<User> findById(Long id);
    void deleteById(Long id);

    boolean existsByEmail(String email);
    boolean existsByLogin(String login);

    PaginatedResponseDTO<User> findAll(int page, int size);
}
