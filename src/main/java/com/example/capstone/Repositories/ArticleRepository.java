package com.example.capstone.Repositories;

import com.example.capstone.Models.Article;
import com.example.capstone.Models.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
//    Article findByAuthor(Profile author);
public List<Article> findByAuthor(Profile author , Sort sort);
    Article findByArticleId(Long articleId);

    List<Article> findByTitle(String title);
}
