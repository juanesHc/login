package com.example.login.controller.exception;

import com.example.login.dto.exception.ErrorDto;
import com.example.login.exception.MessagingException;
import com.example.login.exception.RegisterUserException;
import com.example.login.exception.RetrieveRoleException;
import com.example.login.exception.VerifyUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ErrorDto("VALIDATION_ERROR", "Invalid input", errors);
    }
    @ExceptionHandler(RegisterUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleRegisterUser(RegisterUserException ex) {
        log.warn("Registration failed: {}", ex.getMessage());
        return new ErrorDto("REGISTRATION_FAILED", ex.getMessage(), null);
    }

    @ExceptionHandler(RetrieveRoleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleRetrieveRole(RetrieveRoleException ex) {
        log.warn("Retrieve failed: {}", ex.getMessage());
        return new ErrorDto("RETRIEVE_FAILED", ex.getMessage(), null);
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleSendMessage(MessagingException ex) {
        log.warn("Sending failed: {}", ex.getMessage());
        return new ErrorDto("SENDING_FAILED", ex.getMessage(), null);
    }

    @ExceptionHandler(VerifyUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleVerifyAccount(VerifyUserException ex) {
        log.warn("verify failed: {}", ex.getMessage());
        return new ErrorDto("VERIFY_FAILED", ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleGeneral(Exception ex) {
        log.error("Unexpected error", ex);
        return new ErrorDto("INTERNAL_ERROR", "An unexpected error occurred", null);
    }

}
