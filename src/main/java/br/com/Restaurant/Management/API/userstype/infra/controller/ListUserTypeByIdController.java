package br.com.Restaurant.Management.API.userstype.infra.controller;

import br.com.Restaurant.Management.API.userstype.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.userstype.core.usecase.ListUserTypeByIdUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Types")
@RestController
@RequestMapping("/userstype")
public class ListUserTypeByIdController {

    private final ListUserTypeByIdUseCase listUserTypeByIdUseCase;

    public ListUserTypeByIdController(ListUserTypeByIdUseCase listUserTypeByIdUseCase) {
        this.listUserTypeByIdUseCase = listUserTypeByIdUseCase;
    }

    @GetMapping("/{id}")
    public UserTypeOutputDTO findUserTypeById(@PathVariable Long id){
        return listUserTypeByIdUseCase.execute(id);
    }
}
