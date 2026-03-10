package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.EmailAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.LoginAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import br.com.Restaurant.Management.API.userstype.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;

public class CreateUserUseCase {
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public CreateUserUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    public UserOutputDTO execute(CreateUserInputDTO input) {

        if (userGateway.existsByEmail(input.email())) {
            throw new EmailAlreadyInUseException(input.email());
        }

        if (userGateway.existsByLogin(input.login())) {
            throw new LoginAlreadyInUseException(input.login());
        }

        userTypeGateway.findById(input.type_id())
                .orElseThrow(() -> new UserTypeNotFoundException(input.type_id()));

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
