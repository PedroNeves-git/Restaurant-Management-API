package br.com.Restaurant.Management.API.cuisinetype.infra.config;

import br.com.Restaurant.Management.API.cuisinetype.core.gateway.CuisineTypeGateway;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.CreateCuisineTypeUseCase;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.DeleteCuisineTypeUseCase;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.ListCuisineTypeGatewayByIdUseCase;
import br.com.Restaurant.Management.API.cuisinetype.core.usecase.ListCuisineTypeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuisineTypeBeanConfig {

    @Bean
    public CreateCuisineTypeUseCase createCuisineTypeUseCase(
            CuisineTypeGateway cuisineTypeGateway
    ) {
        return new CreateCuisineTypeUseCase(cuisineTypeGateway);
    }

    @Bean
    public DeleteCuisineTypeUseCase deleteCuisineTypeUseCase(
            CuisineTypeGateway cuisineTypeGateway
    ) {
        return new DeleteCuisineTypeUseCase(cuisineTypeGateway);
    }

    @Bean
    public ListCuisineTypeGatewayByIdUseCase listCuisineTypeGatewayByIdUseCase(CuisineTypeGateway cuisineTypeGateway) {
        return new ListCuisineTypeGatewayByIdUseCase(cuisineTypeGateway);
    }

    @Bean
    public ListCuisineTypeUseCase listCuisineTypeUseCase(CuisineTypeGateway cuisineTypeGateway) {
        return new ListCuisineTypeUseCase(cuisineTypeGateway);
    }

}
