package br.com.Restaurant.Management.API.userstype.infra.config;

import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;
import br.com.Restaurant.Management.API.userstype.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeBeanConfig {

    @Bean
    public CreateUserTypeUseCase createUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new CreateUserTypeUseCase(userTypeGateway);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new DeleteUserTypeUseCase(userTypeGateway);
    }

    @Bean
    public ListUsersTypeUseCase listUsersTypeUseCase(UserTypeGateway userTypeGateway) {
        return new ListUsersTypeUseCase(userTypeGateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new UpdateUserTypeUseCase(userTypeGateway);
    }

    @Bean
    public ListUserTypeByIdUseCase listUserTypeByIdUseCase(UserTypeGateway userTypeGateway) {
        return new ListUserTypeByIdUseCase(userTypeGateway);
    }
}
