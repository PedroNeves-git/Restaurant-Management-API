package br.com.Restaurant.Management.API.userstype.core.usecase;

import br.com.Restaurant.Management.API.common.dto.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.userstype.core.domain.UserType;
import br.com.Restaurant.Management.API.userstype.core.dto.output.UserTypeOutputDTO;
import br.com.Restaurant.Management.API.userstype.core.gateway.UserTypeGateway;

import java.util.List;

public class ListUsersTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public ListUsersTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public PaginatedResponseDTO<UserTypeOutputDTO> execute(int page, int size) {
        PaginatedResponseDTO<UserType> usersType =  userTypeGateway.findAll(page, size);

        List<UserTypeOutputDTO> content = usersType.content().stream()
                .map(UserType::toOutput)
                .toList();

        return new PaginatedResponseDTO<>(
                content,
                usersType.page(),
                usersType.size(),
                usersType.totalElements(),
                usersType.totalPages()
        );
    }
}
