package br.com.Restaurant.Management.API.userstype.core.usecase;

import br.com.Restaurant.Management.API.userstype.core.domain.UserType;
import br.com.Restaurant.Management.API.userstype.core.dto.input.CreateUserTypeInputDTO;
import br.com.Restaurant.Management.API.userstype.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.userstype.core.exception.UserTypeAlreadyInUseException;
import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;

public class CreateUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public CreateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserTypeOutputDTO execute(CreateUserTypeInputDTO input){
        if(userTypeGateway.existsByName(input.name())) {
            throw new UserTypeAlreadyInUseException(input.name());
        }

        UserType userType = UserType.newUserType(
                input.name()
        );

        UserType createdUserType = userTypeGateway.create(userType);
        return createdUserType.toOutput();
    }
}
