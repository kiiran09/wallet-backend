package com.example.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(Long userId) {
        super("Insufficient balance for user with ID: " + userId);
    }
}
