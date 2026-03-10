package br.com.Restaurant.Management.API.userstype.infra.mapper;

import br.com.Restaurant.Management.API.userstype.core.domain.UserType;
import br.com.Restaurant.Management.API.userstype.infra.entity.UserTypeEntity;

public class UserTypeEntityMapper {

    public static UserTypeEntity toEntity(UserType userType) {

        var data = userType.export();

        return new UserTypeEntity(
                data.id(),
                data.name()
        );
    }

    public static UserType toDomain(UserTypeEntity userTypeEntity) {
        return UserType.restore(
                userTypeEntity.getId(),
                userTypeEntity.getName()
        );
    }
}
