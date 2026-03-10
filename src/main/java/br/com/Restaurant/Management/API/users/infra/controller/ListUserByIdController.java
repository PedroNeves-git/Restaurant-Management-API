package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.usecase.ListUserByIdUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users")
@RestController
@RequestMapping("/users")
public class ListUserByIdController {

    private final ListUserByIdUseCase listUserByIdUseCase;

    public ListUserByIdController(ListUserByIdUseCase listUserByIdUseCase) {
        this.listUserByIdUseCase = listUserByIdUseCase;
    }

    @GetMapping("/{id}")
    public UserOutputDTO findById(@PathVariable Long id) {
        return listUserByIdUseCase.execute(id);
    }
}