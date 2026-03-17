package br.com.Restaurant.Management.API.users.infra.mapper;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.infra.entity.UserEntity;

public class UserEntityMapper {

    public static UserEntity toEntity(User user) {

        var data = user.export();

        return new UserEntity(
                data.id(),
                data.name(),
                data.email(),
                data.login(),
                data.password(),
                data.active(),
                data.typeId(),
                data.userRole(),
                data.createdAt(),
                data.updatedAt()
        );
    }

    public static User toDomain(UserEntity e) {
        return User.restore(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getLogin(),
                e.getPassword(),
                e.getActive(),
                e.getTypeId(),
                e.getRole(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
