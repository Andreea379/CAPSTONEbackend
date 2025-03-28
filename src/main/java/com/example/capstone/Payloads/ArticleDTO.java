package com.example.capstone.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
public class ArticleDTO {
    @NotBlank(message = "Title field couldn't be empty")
    @NotNull(message = "Title field couldn't be null")
    private String title;
    @NotBlank(message = "Content field couldn't be empty")
    @NotNull(message = "Content field couldn't be null")
    private String content;
    @NotBlank(message = "Category field couldn't be empty")
    @NotNull(message = "Category field couldn't be null")
    private String category;
    private LocalDate publishedAt;
    @NotBlank(message = "Article image field couldn't be empty")
    @URL
    private String articleImage;
    private String authorProfileImage;
}
