package br.com.Restaurant.Management.API.userstype.core.usecase;

import br.com.Restaurant.Management.API.userstype.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.userstype.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;

public class ListUserTypeByIdUseCase {
    private UserTypeGateway userTypeGateway;

    public ListUserTypeByIdUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserTypeOutputDTO execute(Long id) {
        return userTypeGateway.findById(id)
                .map(userType -> userType.toOutput())
                .orElseThrow(() -> new UserTypeNotFoundException(id));
    }

}
