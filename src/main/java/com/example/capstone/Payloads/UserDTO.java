package com.example.capstone.Payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UserDTO {
    @NotBlank(message = "Username field couldn't be empty")
    @NotNull(message = "Username field couldn't be null")
    private String username;

    @NotBlank(message = "Email field couldn't be empty")
    @NotNull(message = "Email field couldn't be null")
    @Email(message = "Invalid email")
    private String email;


    @NotBlank(message = "Password field couldn't be empty")
    @NotNull(message = "Password field couldn't be null")
    private String password;

    @NotBlank(message = "First name field couldn't be empty")
    @NotNull(message = "First name field couldn't be null")
    private String firstName;

    @NotBlank(message = "Last name field couldn't be empty")
    @NotNull(message = "Last name field couldn't be null")
    private String lastName;

    @URL
    private String avatar;
}
