package br.com.Restaurant.Management.API.cuisinetype.infra.controller;

import br.com.Restaurant.Management.API.cuisinetype.core.dto.input.CreateCuisineTypeInputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.CreateCuisineTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cuisine Types")
@RestController
@RequestMapping("/cuisinetype")
public class CreateCuisineTypeController {
    private final CreateCuisineTypeUseCase createCuisineTypeUseCase;

    public CreateCuisineTypeController(CreateCuisineTypeUseCase createCuisineTypeUseCase) {
        this.createCuisineTypeUseCase = createCuisineTypeUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineTypeOutputDTO create(@RequestBody CreateCuisineTypeInputDTO request){
        return createCuisineTypeUseCase.execute(request);
    }
}
