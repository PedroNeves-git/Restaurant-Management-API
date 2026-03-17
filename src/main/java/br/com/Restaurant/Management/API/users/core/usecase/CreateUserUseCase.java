package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.input.CreateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.exception.EmailAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.LoginAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;
import br.com.Restaurant.Management.API.userstype.core.exception.UserTypeNotFoundException;
import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateUserUseCase {
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway, PasswordEncoder passwordEncoder) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
        this.passwordEncoder = passwordEncoder;
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

        String encryptedPassword = passwordEncoder.encode(input.password());

        User user = User.newUser(
                input.name(),
                input.email(),
                input.login(),
                encryptedPassword,
                input.type_id(),
                input.role()
        );

        User createdUser = userGateway.create(user);
        return createdUser.toOutput();
    }
}
