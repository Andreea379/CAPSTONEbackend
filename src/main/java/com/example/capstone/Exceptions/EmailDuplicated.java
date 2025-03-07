package com.example.capstone.Exceptions;

public class EmailDuplicated extends RuntimeException {
    public EmailDuplicated(String message) {
        super(message);
    }
}
