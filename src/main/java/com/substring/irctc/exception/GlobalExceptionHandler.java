package com.substring.irctc.exception;

import com.substring.irctc.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleException(NoSuchElementException e){
        ErrorResponse errorResponse  = new ErrorResponse("Train not found " + e.getMessage(), "404",false);
        ResponseEntity<ErrorResponse> responseResponseEntity = new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        return responseResponseEntity;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        ErrorResponse errorResponse  = new ErrorResponse( e.getMessage(), "404",false);
        ResponseEntity<ErrorResponse> responseResponseEntity = new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        return responseResponseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errorResponse = new HashMap<>();
       methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error ->{
           String errorMessage = error.getDefaultMessage();
           String fieldName = error.getField();
           errorResponse.put(fieldName, errorMessage);
       });
       ResponseEntity<Map<String, String>> responseResponseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        return responseResponseEntity;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        ErrorResponse errorResponse  = new ErrorResponse( e.getMessage(), "400",false);
        ResponseEntity<ErrorResponse> responseResponseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        return responseResponseEntity;
    }

}
