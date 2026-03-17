package br.com.Restaurant.Management.API.users.infra.repository;

import br.com.Restaurant.Management.API.users.infra.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    UserDetails findByLogin(String login);
}
