package com.example.capstone.Models;

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
    private String title;
    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private Profile author;
    private String content;
    private String category;
    private LocalDate publishedAt;
    private String articleImage;
}
