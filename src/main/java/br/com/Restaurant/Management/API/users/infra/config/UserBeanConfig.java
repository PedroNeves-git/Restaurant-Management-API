package br.com.Restaurant.Management.API.users.infra.config;

import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import br.com.Restaurant.Management.API.users.core.usecase.*;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserBeanConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserGateway userGateway,
            UserTypeGateway userTypeGateway,
            PasswordEncoder passwordEncoder
    ) {
        return new CreateUserUseCase(userGateway, userTypeGateway, passwordEncoder);
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
