package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.dto.input.UpdateUserInputDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.usecase.UpdateUserUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UpdateUserController {

    private final UpdateUserUseCase updateUserUseCase;

    public UpdateUserController(UpdateUserUseCase updateUserUseCase) {
        this.updateUserUseCase = updateUserUseCase;
    }

    @PutMapping("/{id}")
    public UserOutputDTO update(
            @PathVariable Long id,
            @RequestBody UpdateUserInputDTO input
    ) {
        return updateUserUseCase.execute(id, input);
    }
}