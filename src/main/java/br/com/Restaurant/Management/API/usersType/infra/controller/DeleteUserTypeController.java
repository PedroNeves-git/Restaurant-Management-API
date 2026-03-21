package br.com.Restaurant.Management.API.usersType.infra.controller;

import br.com.Restaurant.Management.API.common.dto.BaseResponseDTO;
import br.com.Restaurant.Management.API.usersType.core.usecase.DeleteUserTypeUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Types")
@RestController
@RequestMapping("/userstype")
public class DeleteUserTypeController {

    private final DeleteUserTypeUseCase deleteUserTypeUseCase;

    public DeleteUserTypeController(DeleteUserTypeUseCase deleteUserTypeUseCase) {
        this.deleteUserTypeUseCase = deleteUserTypeUseCase;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<BaseResponseDTO> delete(@PathVariable Long id) {
        deleteUserTypeUseCase.execute(id);

        return ResponseEntity.ok(
                BaseResponseDTO.success(
                        HttpStatus.OK.value(),
                        "USERTYPE_DELETED",
                        "User Type deleted successfully"
                )
        );
    }
}
