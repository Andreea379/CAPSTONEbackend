package com.example.capstone.Services;

import com.example.capstone.Exceptions.ArticleNotFound;
import com.example.capstone.Exceptions.ProfileNotFound;
import com.example.capstone.Models.Article;
import com.example.capstone.Models.Profile;
import com.example.capstone.Payloads.ArticleDTO;
import com.example.capstone.Repositories.ArticleRepository;
import com.example.capstone.Repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ArticleRepository articleRepository;

    public Article saveArticle(ArticleDTO articleDTO, Long profileId){
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFound("Profile not found"));
        Article article =  fromArticleDTOtoArticle(articleDTO);
        article.setAuthor(profile);
        return articleRepository.save(article);
    }

    public List<Article> getArticleByAuthor(Long authorId){
        Profile author = profileRepository.findById(authorId).orElseThrow(() -> new ProfileNotFound("Profile not found"));;
        List<Article> article = articleRepository.findByAuthor(author, Sort.by(Sort.Order.desc("publishedAt")));
        if (article == null) {
            throw new ArticleNotFound("No article found for this author");
        }

        return article;
    }

    public List<Article> getAllArticles(){
        return articleRepository.findAll();
    }

    public Article getArticleById(Long articleId){
        Article articleById = articleRepository.findByArticleId(articleId);

        return articleById;
    }



    public List<Article> getArticleByTitle(String title){
        List<Article> articleByTitle = articleRepository.findByTitle(title);
        if(articleByTitle.isEmpty()){
            throw new ArticleNotFound("Article not found!");
        }
        return articleByTitle;
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
