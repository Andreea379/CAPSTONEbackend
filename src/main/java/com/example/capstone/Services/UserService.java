package com.example.capstone.Services;

import com.example.capstone.Enumerations.Roles;
import com.example.capstone.Exceptions.EmailDuplicated;
import com.example.capstone.Exceptions.UsernameDuplicated;
import com.example.capstone.Models.Role;
import com.example.capstone.Models.User;
import com.example.capstone.Payloads.Requests.RegistrationRequest;
import com.example.capstone.Payloads.UserDTO;
import com.example.capstone.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String saveUser(RegistrationRequest userRequest){
        checkDuplicatedKey(userRequest.getUsername(), userRequest.getEmail());

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        User user = new User(
                userRequest.getUsername(),
                userRequest.getEmail(),
                encodedPassword,
                userRequest.getFirstName(),
                userRequest.getLastName()
        );
        user.setRole(new Role(Roles.USER_ROLE));
        userRepository.save(user);
        return "The user " + user.getUsername() + " with id " +user.getId() + " was saved correctly";
    }

    public void checkDuplicatedKey(String username, String email){
        if(userRepository.existsByUsername(username)){
            throw new UsernameDuplicated("Username already exists!");
        }
        if(userRepository.existsByEmail(email)){
            throw new EmailDuplicated("Email already exists!");
        }
    }


    public User fromUserDTOtoUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAvatar(userDTO.getAvatar());
        return user;
    }

    public UserDTO fromUserToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAvatar(user.getAvatar());
        return userDTO;
    }
}
