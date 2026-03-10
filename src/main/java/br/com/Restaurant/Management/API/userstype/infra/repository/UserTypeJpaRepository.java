package br.com.Restaurant.Management.API.userstype.infra.repository;

import br.com.Restaurant.Management.API.userstype.core.domain.UserType;
import br.com.Restaurant.Management.API.userstype.infra.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTypeJpaRepository extends JpaRepository<UserTypeEntity, Long> {
    boolean existsByName(String name);

    Optional<UserType> findByName(String name);
}
