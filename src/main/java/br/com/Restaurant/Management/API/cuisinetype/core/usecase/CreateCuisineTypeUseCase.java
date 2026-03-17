package br.com.Restaurant.Management.API.cuisinetype.core.usecase;

import br.com.Restaurant.Management.API.cuisinetype.core.domain.CuisineType;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.input.CreateCuisineTypeInputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.dto.output.CuisineTypeOutputDTO;
import br.com.Restaurant.Management.API.cuisinetype.core.exception.CuisineTypeAlreadyInUseException;
import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;

public class CreateCuisineTypeUseCase {

    private final CuisineTypeGateway cuisineTypeGateway;

    public CreateCuisineTypeUseCase(CuisineTypeGateway cuisineTypeGateway) {
        this.cuisineTypeGateway = cuisineTypeGateway;
    }

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
