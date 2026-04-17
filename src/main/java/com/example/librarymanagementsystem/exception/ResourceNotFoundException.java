package com.example.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Automatically returns a 404 Not Found HTTP status when this exception is thrown
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Constructor that accepts a custom error message
    public ResourceNotFoundException(String message) {
        super(message);
    }
}