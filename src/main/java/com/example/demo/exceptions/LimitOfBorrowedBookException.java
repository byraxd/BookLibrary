package com.example.demo.exceptions;

public class LimitOfBorrowedBookException extends RuntimeException {
    public LimitOfBorrowedBookException(String message) {
        super(message);
    }
}
