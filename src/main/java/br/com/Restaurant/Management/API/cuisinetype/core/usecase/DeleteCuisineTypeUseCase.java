package br.com.Restaurant.Management.API.cuisinetype.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;

public class DeleteCuisineTypeUseCase {

    private final CuisineTypeGateway cuisineTypeGateway;

    public DeleteCuisineTypeUseCase(CuisineTypeGateway cuisineTypeGateway) {
        this.cuisineTypeGateway = cuisineTypeGateway;
    }

    public void execute(Long id){
        cuisineTypeGateway.findById(id).orElseThrow(() -> new CuisineTypeNotFoundException(id));
        cuisineTypeGateway.deleteById(id);
    }
}
