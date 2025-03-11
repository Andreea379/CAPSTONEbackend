package com.example.capstone.Repositories;

import com.example.capstone.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByAuthor_User_Id(Long userId);
    List<Article> findByArticleId(Long articleId);
}
