package com.zvrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtime(RuntimeException ex) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> login(BadCredentialsException ex) {

        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Invalid username or password"
        );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);

    }

    private ResponseEntity<Object> buildResponse(
            HttpStatus status,
            String message) {

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", LocalDateTime.now());

        body.put("status", status.value());

        body.put("message", message);

        return ResponseEntity.status(status).body(body);

    }

}