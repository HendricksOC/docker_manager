package com.hendrickoc.docker_manager.exception;

import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.hendrickoc.docker_manager.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        var error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                "O recurso solicitado não existe no Docker: " + ex.getMessage(),
                LocalDateTime.now(ZoneId.of("UTC"))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NotModifiedException.class)
    public ResponseEntity<ErrorResponse> handleNotModified(NotModifiedException ex) {
        var error = new ErrorResponse(
                HttpStatus.NOT_MODIFIED.value(),
                "Not Modified",
                "O contêiner já se encontra no estado solicitado." + ex.getMessage(),
                LocalDateTime.now(ZoneId.of("UTC"))
        );
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        var error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                "Conflito de recursos no Docker: " + ex.getMessage(),
                LocalDateTime.now(ZoneId.of("UTC"))
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
