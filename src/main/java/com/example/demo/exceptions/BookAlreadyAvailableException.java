package com.example.demo.exceptions;

public class BookAlreadyAvailableException extends RuntimeException {
    public BookAlreadyAvailableException(String message) {
        super(message);
    }
}
