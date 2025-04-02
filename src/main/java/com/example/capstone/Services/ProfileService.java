package com.example.capstone.Services;

import com.example.capstone.Exceptions.ProfileNotFound;
import com.example.capstone.Exceptions.UserNotFound;
import com.example.capstone.Models.Article;
import com.example.capstone.Models.Profile;
import com.example.capstone.Models.User;
import com.example.capstone.Payloads.ProfileDTO;
import com.example.capstone.Payloads.Requests.RegistrationRequest;
import com.example.capstone.Repositories.ArticleRepository;
import com.example.capstone.Repositories.ProfileRepository;
import com.example.capstone.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ArticleRepository articleRepository;


    public Optional<Profile> getProfileById(Long profileId) {
        return profileRepository.findById(profileId);
    }

    public ProfileDTO getProfileByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found!"));
        Optional<Profile> profile = profileRepository.findById(user.getId());
        return fromProfileToProfileDTO(profile.orElseThrow(()->new ProfileNotFound("Profile not found!")));
    }
    public Profile getProfileByUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found"));
        Profile profile = profileRepository.findByUser(user);
        return profile;
    }

    public List<Profile> getProfileByFirstName(String firstName){
        List<Profile> profileByFirstName = profileRepository.findByFirstName(firstName);
        if (profileByFirstName.isEmpty()) {
            throw new ProfileNotFound("Profile not found!");
        }
        return profileByFirstName;
    }

public Profile getProfileByArticleId(Long articleId){
        Optional<Article> article = articleRepository.findById(articleId);
        Profile profile = profileRepository.findByArticle(article);
        return profile;
}
    public Profile updateProfile(ProfileDTO profileDTO, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found"));
        Profile profile = profileRepository.findByUser(user);
        fromProfileToProfileDTO(profile);
        profile.setProfession(profileDTO.getProfession());
        profile.setDescription(profileDTO.getDescription());
        profile.setProfileImage(profileDTO.getProfileImage());
        profile.setPublicationDate(LocalDate.now());
        return profileRepository.save(profile);

    }


    //travaso
    public Profile fromProfileDTOtoProfile(ProfileDTO profileDTO){
        Profile profile = new Profile();
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setProfession(profileDTO.getProfession());
        profile.setDescription(profileDTO.getDescription());
        profile.setPublicationDate(LocalDate.now());
        profile.setProfileImage(profileDTO.getProfileImage());
        return profile;
    }

    public ProfileDTO fromProfileToProfileDTO(Profile profile){
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(profile.getFirstName());
        profileDTO.setLastName(profile.getLastName());
        profileDTO.setProfession(profile.getProfession());
        profileDTO.setDescription(profile.getDescription());
        profileDTO.setPublicationDate(LocalDate.now());
        profileDTO.setProfileImage(profile.getProfileImage());
        return profileDTO;
    }
}
