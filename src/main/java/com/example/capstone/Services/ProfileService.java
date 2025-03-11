package com.example.capstone.Services;

import com.example.capstone.Exceptions.UserNotFound;
import com.example.capstone.Models.Profile;
import com.example.capstone.Models.User;
import com.example.capstone.Payloads.ProfileDTO;
import com.example.capstone.Repositories.ProfileRepository;
import com.example.capstone.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;

    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    public ProfileDTO getProfileByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found!"));
        Profile profile = profileRepository.findByUserId(user.getId());
        return fromProfileToProfileDTO(profile);
    }

    public ProfileDTO getProfileByFirstName(String firstName){
        Profile profileByFirstName = profileRepository.findByFirstName(firstName);
        return fromProfileToProfileDTO(profileByFirstName);
    }

    public ProfileDTO getProfileByLastName(String lastName){
        Profile profileByLastName = profileRepository.findByLastName(lastName);
        return fromProfileToProfileDTO(profileByLastName);
    }

    public Profile updateProfile(ProfileDTO profileDTO, String firstName){
        Profile profile = profileRepository.findByFirstName(firstName);
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setProfession(profileDTO.getProfession());
        profile.setDescription(profileDTO.getDescription());
        profile.setPublicationDate(profileDTO.getPublicationDate());
        profile.setFollowers(profileDTO.getFollowers());
        profile.setFollowing(profileDTO.getFollowing());
        profile.setProfileImage(profileDTO.getProfileImage());
        return profileRepository.save(profile);

    }


    //travaso
    public Profile fromProfileDTOtoProfile(ProfileDTO profileDTO){
        Profile profile = new Profile();
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setProfession(profileDTO.getProfession());
        profile.setDescription(profileDTO.getDescription());
        profile.setPublicationDate(profileDTO.getPublicationDate());
        profile.setFollowers(profileDTO.getFollowers());
        profile.setFollowing(profileDTO.getFollowing());
        profile.setProfileImage(profileDTO.getProfileImage());
        return profile;
    }

    public ProfileDTO fromProfileToProfileDTO(Profile profile){
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(profile.getFirstName());
        profileDTO.setLastName(profile.getLastName());
        profileDTO.setProfession(profile.getProfession());
        profileDTO.setDescription(profile.getDescription());
        profileDTO.setPublicationDate(profile.getPublicationDate());
        profileDTO.setFollowers(profile.getFollowers());
        profileDTO.setFollowing(profile.getFollowing());
        profileDTO.setProfileImage(profile.getProfileImage());
        return profileDTO;
    }
}
