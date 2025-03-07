package com.example.capstone.Payloads.Requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username field could not be empty")
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank(message = "Password field could not be empty")
    @Size(min = 3, max = 20)
    private String password;
}
