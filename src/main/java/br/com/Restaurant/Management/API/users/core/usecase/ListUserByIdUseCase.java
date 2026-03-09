package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

public class ListUserByIdUseCase {

    private final UserGateway userGateway;

    public ListUserByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserOutputDTO execute(Long id) {
        return userGateway.findById(id)
                .map(user -> user.toOutput())
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}