package br.com.Restaurant.Management.API.userstype.core.usecase;

import br.com.Restaurant.Management.API.userstype.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;

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
