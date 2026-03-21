package br.com.Restaurant.Management.API.usersType.core.usecase;

import br.com.Restaurant.Management.API.usersType.core.domain.UserType;
import br.com.Restaurant.Management.API.usersType.core.dto.input.UpdateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeAlreadyInUseException;
import br.com.Restaurant.Management.API.usersType.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.usersType.core.gateway.UserTypeGateway;

public class UpdateUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public UpdateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserTypeOutputDTO execute(Long id, UpdateUserTypeInputDTO input) {
        UserType userType = userTypeGateway.findById(id)
                .orElseThrow(() -> new UserTypeNotFoundException(id));

        if (!userType.export().name().equals(input.name()) &&
                userTypeGateway.existsByName(input.name())) {
            throw new UserTypeAlreadyInUseException(input.name());
        }

        userType.update(
                input.name()
        );

        UserType updated = userTypeGateway.update(userType);
        return updated.toOutput();
    }
}
