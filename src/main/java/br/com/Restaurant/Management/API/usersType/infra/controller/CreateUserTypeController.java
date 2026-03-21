package br.com.Restaurant.Management.API.usersType.infra.controller;

import br.com.Restaurant.Management.API.usersType.core.dto.input.CreateUserTypeInputDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.usersType.core.usecase.CreateUserTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Types")
@RestController
@RequestMapping("/userstype")
public class CreateUserTypeController {
    private final CreateUserTypeUseCase createUserTypeUseCase;

    public CreateUserTypeController(CreateUserTypeUseCase createUserTypeUseCase) {
        this.createUserTypeUseCase = createUserTypeUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTypeOutputDTO create(@RequestBody CreateUserTypeInputDTO request){
        return createUserTypeUseCase.execute(request);
    }
}
