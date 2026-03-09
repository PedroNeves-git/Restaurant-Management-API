package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.dto.common.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.usecase.ListUsersUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ListUsersController {

    private final ListUsersUseCase listUsersUseCase;

    public ListUsersController(ListUsersUseCase listUsersUseCase) {
        this.listUsersUseCase = listUsersUseCase;
    }

    @GetMapping
    public PaginatedResponseDTO<UserOutputDTO> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return listUsersUseCase.execute(page, size);
    }
}