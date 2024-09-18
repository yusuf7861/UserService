package com.lcwd.user.service.userservice.exceptions;

import com.lcwd.user.service.userservice.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// handles exceptions occured anywhere
@RestControllerAdvice
public class GlobalExceptionHandler {

    // if any exception generated in your program then this method will automatically called
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex)
    {
        // Extract the exception message
        String message = ex.getMessage();

        // Create an ApiResponse object using the builder pattern, setting the message, success flag, and HTTP status
        ApiResponse response = ApiResponse.builder()
            .message(message)
            .success(true)
            .status(HttpStatus.NOT_FOUND)
            .build();

        // Return a ResponseEntity containing the ApiResponse object and the HTTP status
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
