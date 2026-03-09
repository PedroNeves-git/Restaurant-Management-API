package br.com.Restaurant.Management.API.users.infra.http;

import br.com.Restaurant.Management.API.users.core.exception.EmailAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.InvalidFieldException;
import br.com.Restaurant.Management.API.users.core.exception.LoginAlreadyInUseException;
import br.com.Restaurant.Management.API.users.core.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ErrorResponseBadRequest> handleInvalidField(InvalidFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseBadRequest(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getCode(),
                        ex.getField(),
                        ex.getMessage(),
                        OffsetDateTime.now()
                )
        );
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "EMAIL_ALREADY_IN_USE",
                        ex.getMessage(),
                        OffsetDateTime.now()
                )
        );
    }

    @ExceptionHandler(LoginAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleLoginAlreadyInUse(LoginAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "LOGIN_ALREADY_IN_USE",
                        ex.getMessage(),
                        OffsetDateTime.now()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "INTERNAL_ERROR",
                        "Erro interno do servidor",
                        OffsetDateTime.now()
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "USER_NOT_FOUND",
                        ex.getMessage(),
                        OffsetDateTime.now()
                )
        );
    }
}