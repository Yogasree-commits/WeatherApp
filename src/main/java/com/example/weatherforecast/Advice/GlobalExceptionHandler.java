package com.example.weatherforecast.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.class)
//    public ResponseEntity<String> handleHttpClientErrorException(org.springframework.web.client.HttpClientErrorException ex) {
//        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found.");
//        }
//        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        // Log the exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
