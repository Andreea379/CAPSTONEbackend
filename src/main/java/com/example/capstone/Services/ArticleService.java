package com.example.capstone.Services;

import com.example.capstone.Exceptions.ArticleNotFound;
import com.example.capstone.Models.Article;
import com.example.capstone.Payloads.ArticleDTO;
import com.example.capstone.Repositories.ArticleRepository;
import com.example.capstone.Repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ArticleRepository articleRepository;

    public Long saveArticle(Article article){
        articleRepository.save(article);
        return article.getArticleId();
    }

    public Article updateArticle(ArticleDTO articleDTO, Long articleId){
        List<Article> articleFound = articleRepository.findByArticleId(articleId);
        if(articleFound.isEmpty()){
            throw new ArticleNotFound("Article not found!");
        }
        Article article = articleFound.get(0);
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setCategory(articleDTO.getCategory());
        article.setArticleImage(articleDTO.getArticleImage());
        articleRepository.save(article);

        return article;
    }

    //travaso
    public Article fromArticleDTOtoArticle(ArticleDTO articleDTO){
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setCategory(articleDTO.getCategory());
        article.setPublishedAt(articleDTO.getPublishedAt());
        article.setArticleImage(articleDTO.getArticleImage());
        return article;
    }

    public ArticleDTO fromArticleToArticleDTO(Article article){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setCategory(article.getCategory());
        articleDTO.setPublishedAt(article.getPublishedAt());
        articleDTO.setArticleImage(article.getArticleImage());
        return articleDTO;
    }
}
