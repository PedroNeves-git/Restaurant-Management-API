package br.com.Restaurant.Management.API.usersType.infra.controller;

import br.com.Restaurant.Management.API.usersType.core.dto.input.UpdateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.usecase.UpdateUserTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Types")
@RestController
@RequestMapping("/userstype")
public class UpdateUserTypeController {

    private final UpdateUserTypeUseCase updateUserTypeUseCase;

    public UpdateUserTypeController(UpdateUserTypeUseCase updateUserTypeUseCase) {
        this.updateUserTypeUseCase = updateUserTypeUseCase;
    }

    @PutMapping("/{id}")
    public UserTypeOutputDTO update(
            @PathVariable Long id,
            @RequestBody UpdateUserTypeInputDTO input
    ) {
        return updateUserTypeUseCase.execute(id, input);
    }
}
