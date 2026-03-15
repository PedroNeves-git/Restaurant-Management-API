package br.com.Restaurant.Management.API.restaurant.infra.controller;

import br.com.Restaurant.Management.API.restaurant.core.dto.output.RestaurantOutputDTO;
import br.com.Restaurant.Management.API.restaurant.core.usecase.ListRestaurantsByOwnerIdUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Restaurant")
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class ListRestaurantsByOwnerIdController {

    private final ListRestaurantsByOwnerIdUseCase listRestaurantsByOwnerIdUseCase;

    @GetMapping("/{id}")
    public List<RestaurantOutputDTO> findById(@PathVariable Long id) {
        return listRestaurantsByOwnerIdUseCase.execute(id);
    }
}