package br.com.Restaurant.Management.API.users.infra.config;

import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import br.com.Restaurant.Management.API.users.core.usecase.*;
import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserGateway userGateway,
            UserTypeGateway userTypeGateway
    ) {
        return new CreateUserUseCase(userGateway, userTypeGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(
            UserGateway userGateway
    ) {
        return new DeleteUserUseCase(userGateway);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserGateway userGateway) {
        return new ListUsersUseCase(userGateway);
    }

    @Bean
    public ListUserByIdUseCase listUserByIdUseCase(UserGateway userGateway) {
        return new ListUserByIdUseCase(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserGateway userGateway,  UserTypeGateway userTypeGateway) {
        return new UpdateUserUseCase(userGateway, userTypeGateway);
    }
}
