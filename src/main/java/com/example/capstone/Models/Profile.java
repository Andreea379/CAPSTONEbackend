package com.example.capstone.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfile;
    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String profession;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate publicationDate;
    @Column(nullable = false)
    private int followers;
    @Column(nullable = false)
    private int following;
    @Column(nullable = false)
    private String profileImage;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Article> article;
}
