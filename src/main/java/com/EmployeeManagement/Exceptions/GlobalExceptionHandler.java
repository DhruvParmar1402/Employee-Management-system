package com.EmployeeManagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionsDto>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ExceptionsDto> errors=new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.add(new ExceptionsDto(fieldName,errorMessage,HttpStatus.BAD_REQUEST));
        });
        Collections.reverse(errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionsDto> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ExceptionsDto(ex.getClass().getSimpleName(),ex.getMessage(), HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }
}
