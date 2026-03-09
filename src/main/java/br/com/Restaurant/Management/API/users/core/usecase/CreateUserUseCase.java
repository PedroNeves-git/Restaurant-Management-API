package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.EmailAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.LoginAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserOutputDTO execute(CreateUserInputDTO input) {

        if (userGateway.existsByEmail(input.email())) {
            throw new EmailAlreadyInUseException(input.email());
        }

        if (userGateway.existsByLogin(input.login())) {
            throw new LoginAlreadyInUseException(input.login());
        }

        User user = User.newUser(
                input.name(),
                input.email(),
                input.login(),
                input.password(),
                input.type_id()
        );

        User createdUser = userGateway.create(user);
        return createdUser.toOutput();
    }
}
