package com.example.capstone.Repositories;

import com.example.capstone.Models.Profile;
import com.example.capstone.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);

    List<Profile> findByFirstName(String firstName);
}
