package br.com.Restaurant.Management.API.cuisinetype.infra.controller;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.ListCuisineTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cuisine Types")
@RestController
@RequestMapping("/cuisinetype")
public class ListCuisineTypeController {

    private final ListCuisineTypeUseCase listCuisineTypeUseCase;

    public ListCuisineTypeController(ListCuisineTypeUseCase listCuisineTypeUseCase) {
        this.listCuisineTypeUseCase = listCuisineTypeUseCase;
    }

    @GetMapping
    public PaginatedResponseDTO<CuisineTypeOutputDTO> findAllUserTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return listCuisineTypeUseCase.execute(page, size);
    }
}
