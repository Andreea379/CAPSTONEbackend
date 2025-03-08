package com.example.capstone.Repositories;

import com.example.capstone.Models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUserId(Long userId);
    Profile findByFirstName(String firstName);
    Profile findByLastName(String lastName);
}
