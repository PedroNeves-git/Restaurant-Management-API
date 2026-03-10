package br.com.Restaurant.Management.API.userstype.infra.controller;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.userstype.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.userstype.core.usecase.ListUsersTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Types")
@RestController
@RequestMapping("/userstype")
public class ListUsersTypeController {

    private final ListUsersTypeUseCase listUsersTypeUseCase;

    public ListUsersTypeController(ListUsersTypeUseCase listUsersTypeUseCase) {
        this.listUsersTypeUseCase = listUsersTypeUseCase;
    }

    @GetMapping
    public PaginatedResponseDTO<UserTypeOutputDTO> findAllUserTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return listUsersTypeUseCase.execute(page, size);
    }
}
