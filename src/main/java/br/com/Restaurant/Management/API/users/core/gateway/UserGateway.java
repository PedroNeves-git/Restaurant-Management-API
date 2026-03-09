package br.com.Restaurant.Management.API.users.core.gateway;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.common.PaginatedResponseDTO;

import java.util.Optional;

public interface UserGateway {
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);

    Optional<User> findById(Long id);

    User create(User user);
    User update(User user);
    void deleteById(Long id);

    PaginatedResponseDTO<User> findAll(int page, int size);


}
