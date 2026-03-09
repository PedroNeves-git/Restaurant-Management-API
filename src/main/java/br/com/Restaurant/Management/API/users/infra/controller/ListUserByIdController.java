package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.usecase.ListUserByIdUseCase;
import org.springframework.web.bind.annotation.*;

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