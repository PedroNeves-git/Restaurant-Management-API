package br.com.Restaurant.Management.API.cuisinetype.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeNotFoundException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCuisineTypeUseCase {

    private final CuisineTypeGateway cuisineTypeGateway;

    public void execute(Long id){
        cuisineTypeGateway.findById(id).orElseThrow(() -> new CuisineTypeNotFoundException(id));
        cuisineTypeGateway.deleteById(id);
    }
}
