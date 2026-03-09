package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

public class DeleteUserUseCase {
        private final UserGateway userGateway;

        public DeleteUserUseCase(UserGateway userGateway) {
            this.userGateway = userGateway;
        }

        public void execute(Long id) {
            userGateway.findById(id).orElseThrow(() -> new UserNotFoundException(id));
            userGateway.deleteById(id);
        }
}
