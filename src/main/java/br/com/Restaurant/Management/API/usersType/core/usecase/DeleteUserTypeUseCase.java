package br.com.Restaurant.Management.API.usersType.core.usecase;

import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;

public class DeleteUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public DeleteUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public void execute(Long id){
        userTypeGateway.findById(id).orElseThrow(() -> new UserTypeNotFoundException(id));
        userTypeGateway.deleteById(id);
    }
}
