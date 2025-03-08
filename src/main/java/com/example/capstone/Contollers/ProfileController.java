package com.example.capstone.Contollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.capstone.Exceptions.ProfileNotFound;
import com.example.capstone.Models.Profile;
import com.example.capstone.Payloads.ProfileDTO;
import com.example.capstone.Repositories.ProfileRepository;
import com.example.capstone.Services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    ProfileService profileService;
    @Autowired
    Cloudinary cloudinary;
    @Autowired
    ProfileRepository profileRepository;

    @GetMapping("/findBy/username/{username}")
    public ResponseEntity<?> getProfileByUsername( @PathVariable String  username){
        try{
            ProfileDTO profileDTO1 = profileService.getProfileByUsername(username);
            return new ResponseEntity<>(profileDTO1, HttpStatus.OK);
        }catch (ProfileNotFound p){
            return new ResponseEntity<>("None profile found!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findBy/firstName/{firstName}")
    public ResponseEntity<?> getProfileByFirstName( @PathVariable String firstName){
        try{
            ProfileDTO profileDTO1 = profileService.getProfileByFirstName(firstName);
            return new ResponseEntity<>(profileDTO1, HttpStatus.OK);
        }catch (ProfileNotFound p){
            return new ResponseEntity<>("None profile found!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findBy/lastName/{lastName}")
    public ResponseEntity<?> getProfileByLastName( @PathVariable String lastName){
        try{
            ProfileDTO profileDTO1 = profileService.getProfileByLastName(lastName);
            return new ResponseEntity<>(profileDTO1, HttpStatus.OK);
        }catch (ProfileNotFound p){
            return new ResponseEntity<>("None profile found!", HttpStatus.BAD_REQUEST);
        }
    }
//    @RequestPart("user") @Validated RegistrationRequest newUser, BindingResult validation, @RequestPart(value = "avatar") MultipartFile avatar

    @PatchMapping("/update/{firstName}")
    public ResponseEntity<?> updateProfile(@RequestPart("profile") ProfileDTO profileDTO, @PathVariable String firstName, @RequestPart(value = "profileImage") MultipartFile profileImage){

        try{
            Map mapUpload = cloudinary.uploader().upload(profileImage.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mapUpload.get("secure_url").toString();
            profileDTO.setProfileImage(urlImage);
            Profile updateProfile = profileService.updateProfile(profileDTO, firstName);
            return new ResponseEntity<>("Profile updated!", HttpStatus.CREATED);
        }catch (ProfileNotFound | IOException p){
            return new ResponseEntity<>("Profile not found!", HttpStatus.BAD_REQUEST);
        }
    }
}
