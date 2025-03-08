package com.example.capstone.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
@Data
public class ProfileDTO {
    @NotBlank(message = "First name field couldn't be empty")
    @NotNull(message = "First name field couldn't be null")
    private String firstName;
    @NotBlank(message = "Last name field couldn't be empty")
    @NotNull(message = "Last name field couldn't be null")
    private String lastName;
    @NotBlank(message = "Username field couldn't be empty")
    @NotNull(message = "Username field couldn't be null")
    private String username;
    @NotBlank(message = "Email field couldn't be empty")
    @Email(message = "The email is invalid!")
    private String email;
    @NotBlank(message = "Profession field couldn't be empty")
    @NotNull(message = "Profession field couldn't be null")
    private String profession;
    @NotBlank(message = "Description field couldn't be empty")
    @NotNull(message = "Description field couldn't be null")
    private String description;
    @NotNull(message = "Publication date field couldn't be null")
    private LocalDate publicationDate;
    private int followers;
    private int following;
    @NotBlank(message = "Profile image field couldn't be empty")
    @URL
    private String profileImage;

}
