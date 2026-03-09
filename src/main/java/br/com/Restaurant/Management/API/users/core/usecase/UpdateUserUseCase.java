package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.input.UpdateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.EmailAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.LoginAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

public class UpdateUserUseCase {
    private final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserOutputDTO execute(Long id, UpdateUserInputDTO input) {
        User user = userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.export().email().equals(input.email()) &&
                userGateway.existsByEmail(input.email())) {
            throw new EmailAlreadyInUseException(input.email());
        }

        if (!user.export().login().equals(input.login()) &&
                userGateway.existsByLogin(input.login())) {
            throw new LoginAlreadyInUseException(input.login());
        }

        user.update(
                input.name(),
                input.email(),
                input.login(),
                input.typeId(),
                input.active()
        );

        User updated = userGateway.update(user);
        return updated.toOutput();
    }
}