package com.example.capstone.Contollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.capstone.Exceptions.ProfileNotFound;
import com.example.capstone.Exceptions.UserNotFound;
import com.example.capstone.Models.Article;
import com.example.capstone.Models.Profile;
import com.example.capstone.Models.User;
import com.example.capstone.Payloads.ArticleDTO;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfileByUser(@PathVariable Long userId) {
        try {
            Profile profile = profileService.getProfileByUser(userId);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findBy")
    public ResponseEntity<?> getProfileByFirstName( @RequestParam String firstName){
        try{
            List<Profile> profileList = profileService.getProfileByFirstName(firstName);
            return new ResponseEntity<>(profileList, HttpStatus.OK);
        }catch (ProfileNotFound p){
            return new ResponseEntity<>("None profile found!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateProfile(@RequestPart("profile") ProfileDTO profileDTO, @PathVariable Long userId, @RequestPart(value = "profileImage") MultipartFile profileImage){

        try{
            Map mapUpload = cloudinary.uploader().upload(profileImage.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mapUpload.get("secure_url").toString();
            profileDTO.setProfileImage(urlImage);
            profileService.updateProfile(profileDTO, userId);
            return new ResponseEntity<>(profileDTO, HttpStatus.CREATED);
        }catch (ProfileNotFound | IOException p){
            return new ResponseEntity<>("Profile not found!", HttpStatus.BAD_REQUEST);
        }
    }
}
