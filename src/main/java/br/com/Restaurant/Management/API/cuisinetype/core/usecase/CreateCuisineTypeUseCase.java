package br.com.Restaurant.Management.API.cuisinetype.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.input.CreateCuisineTypeInputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeAlreadyInUseException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCuisineTypeUseCase {

    private final CuisineTypeGateway cuisineTypeGateway;

    public CuisineTypeOutputDTO execute(CreateCuisineTypeInputDTO input){
        if(cuisineTypeGateway.existsByName(input.name())) {
            throw new CuisineTypeAlreadyInUseException(input.name());
        }

        CuisineType userType = CuisineType.newCuisineType(
                input.name()
        );

        CuisineType createdUserType = cuisineTypeGateway.create(userType);
        return createdUserType.toOutput();
    }
}
