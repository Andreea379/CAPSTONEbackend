package com.example.capstone.Exceptions;

public class ProfileNotFound extends RuntimeException {
    public ProfileNotFound(String message) {
        super(message);
    }
}
