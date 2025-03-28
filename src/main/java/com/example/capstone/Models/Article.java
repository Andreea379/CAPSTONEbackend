package com.example.capstone.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Profile author;
    @Column(nullable = false)
    private String content;
    private String category;
    @Column(nullable = false)
    private LocalDate publishedAt;
    @Column(nullable = false)
    private String articleImage;
    private String authorProfileImage;
}
