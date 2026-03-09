package br.com.Restaurant.Management.API.users.core.usecase;

import br.com.Restaurant.Management.API.users.core.domain.User;
import br.com.Restaurant.Management.API.users.core.dto.common.PaginatedResponseDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.gateway.UserGateway;

import java.util.List;

public class ListUsersUseCase {
    private final UserGateway userGateway;

    public ListUsersUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public PaginatedResponseDTO<UserOutputDTO> execute(int page, int size) {
        PaginatedResponseDTO<User> users = userGateway.findAll(page, size);

        List<UserOutputDTO> content = users.content().stream()
                .map(User::toOutput)
                .toList();

        return new PaginatedResponseDTO<>(
                content,
                users.page(),
                users.size(),
                users.totalElements(),
                users.totalPages()
        );
    }
}