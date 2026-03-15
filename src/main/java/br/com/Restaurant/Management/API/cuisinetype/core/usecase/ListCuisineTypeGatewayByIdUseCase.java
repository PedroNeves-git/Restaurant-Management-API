package br.com.Restaurant.Management.API.cuisinetype.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCuisineTypeGatewayByIdUseCase {

    private CuisineTypeGateway cuisineTypeGateway;

    public CuisineTypeOutputDTO execute(Long id) {
        return cuisineTypeGateway.findById(id)
                .map(cuisineType -> cuisineType.toOutput())
                .orElseThrow(() -> new CuisineTypeNotFoundException(id));
    }

}
