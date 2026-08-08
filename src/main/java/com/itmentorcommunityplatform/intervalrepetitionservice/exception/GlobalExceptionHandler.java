package com.itmentorcommunityplatform.intervalrepetitionservice.exception;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidation(ValidationException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDto(e.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidation(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDto(e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleUnknown(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(new ErrorDto(e.getMessage()));
    }

}
