package com.example.capstone.Payloads.Requests;

import com.example.capstone.Models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Username field couldn't be empty")
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank(message = "Email field couldn't be empty")
    @Email(message = "Email non valida")
    @Size(min = 3, max = 30)
    private String email;
    @NotBlank(message = "Password field couldn't be empty")
    @Size(min = 3, max = 20)
    private String password;

    @NotBlank(message = "First name field couldn't be empty")
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank(message = "Last name field couldn't be empty")
    @Size(min = 3, max = 20)
    private String lastName;



    private Role role;

    @URL
    private String avatar;
}
