package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.common.dto.BaseResponseDTO;
import br.com.Restaurant.Management.API.users.core.usecase.DeleteUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users")
@RestController
@RequestMapping("/users")
public class DeleteUserController {

    private final DeleteUserUseCase deleteUserUseCase;

    public DeleteUserController(DeleteUserUseCase deleteUserUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<BaseResponseDTO> delete(@PathVariable Long id) {
        deleteUserUseCase.execute(id);

        return ResponseEntity.ok(
                BaseResponseDTO.success(
                        HttpStatus.OK.value(),
                        "USER_DELETED",
                        "User deleted successfully"
                )
        );
    }
}