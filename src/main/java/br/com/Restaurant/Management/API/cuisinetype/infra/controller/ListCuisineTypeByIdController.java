package br.com.Restaurant.Management.API.cuisinetype.infra.controller;

import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.ListCuisineTypeGatewayByIdUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cuisine Types")
@RestController
@RequestMapping("/cuisinetype")
@RequiredArgsConstructor
public class ListCuisineTypeByIdController {

    private final ListCuisineTypeGatewayByIdUseCase listCuisineTypeGatewayByIdUseCase;

    @GetMapping("/{id}")
    public CuisineTypeOutputDTO findCuisineTypeById(@PathVariable Long id){
        return listCuisineTypeGatewayByIdUseCase.execute(id);
    }
}
