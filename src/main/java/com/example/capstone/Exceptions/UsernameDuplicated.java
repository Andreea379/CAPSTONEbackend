package com.example.capstone.Exceptions;

public class UsernameDuplicated extends RuntimeException {
    public UsernameDuplicated(String message) {
        super(message);
    }
}
