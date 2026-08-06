package com.itmentorcommunityplatform.intervalrepetitionservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ValidationException.class)

    public ResponseEntity<Void> handleValidation(ValidationException e) {
        return ResponseEntity.badRequest().build();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleUnknown(Exception e) {
        return ResponseEntity.internalServerError().build();
    }



}
