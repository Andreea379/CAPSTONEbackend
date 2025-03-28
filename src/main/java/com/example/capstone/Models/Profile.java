package com.example.capstone.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    private String firstName;

    private String lastName;
    @Column(nullable = false)
    private String profession;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false)
    private String profileImage;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Article> article;
}
