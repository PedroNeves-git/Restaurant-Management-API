package br.com.Restaurant.Management.API.users.infra.controller;

import br.com.Restaurant.Management.API.users.core.dto.output.LoginDTO;
import br.com.Restaurant.Management.API.users.core.dto.output.LoginResponseDTO;
import br.com.Restaurant.Management.API.users.infra.entity.UserEntity;
import br.com.Restaurant.Management.API.users.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    LoginController(AuthenticationManager authenticationManager,TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
