package com.example.capstone.Contollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.capstone.Enumerations.Roles;
import com.example.capstone.Exceptions.EmailDuplicated;
import com.example.capstone.Exceptions.UserNotFound;
import com.example.capstone.Exceptions.UsernameDuplicated;
import com.example.capstone.Models.Article;
import com.example.capstone.Models.Role;
import com.example.capstone.Payloads.Requests.LoginRequest;
import com.example.capstone.Payloads.Requests.RegistrationRequest;
import com.example.capstone.Payloads.Response.JwtResponse;
import com.example.capstone.Security.JWT.JwtUtils;
import com.example.capstone.Security.Services.UserDetailsImpl;
import com.example.capstone.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@RequestPart("user") @Validated RegistrationRequest newUser, BindingResult validation
    ){

        if(validation.hasErrors()){
            StringBuilder message = new StringBuilder("Problems with the validation: \n");

            for(ObjectError error : validation.getAllErrors()){
                message.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        }

        try{
            newUser.setRole(new Role(Roles.USER_ROLE));
            userService.saveUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);

        } catch (
                EmailDuplicated | UsernameDuplicated e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult validation){

        if(validation.hasErrors()){
            StringBuilder message = new StringBuilder("Problems with the validation: \n");

            for(ObjectError error : validation.getAllErrors()){
                message.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        }

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.createJWTToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> webRoles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .toList();


            JwtResponse jwtResponse = new JwtResponse(
                    userDetails.getUsername(),
                    userDetails.getId(),
                    userDetails.getEmail(),
                    webRoles,
                    token
            );

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }


}
