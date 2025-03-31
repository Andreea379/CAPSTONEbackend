package com.example.capstone.Contollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.capstone.Exceptions.ProfileNotFound;
import com.example.capstone.Models.Article;
import com.example.capstone.Payloads.ArticleDTO;
import com.example.capstone.Repositories.ArticleRepository;
import com.example.capstone.Repositories.ProfileRepository;
import com.example.capstone.Services.ArticleService;
import com.example.capstone.Services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    Cloudinary cloudinary;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ProfileService profileService;
    @Autowired
    ArticleRepository articleRepository;

    @PostMapping("/newArticle/{profileId}")
    public ResponseEntity<?> newArticle(@RequestPart("article") @Validated ArticleDTO articleDTO, BindingResult validation, @PathVariable Long profileId, @RequestPart(value = "articleImage") MultipartFile articleImage){
        if(validation.hasErrors()){
            StringBuilder message = new StringBuilder("Problems with the validation: \n");

            for(ObjectError error : validation.getAllErrors()){
                message.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        }
        try{

            Map mapUpload = cloudinary.uploader().upload(articleImage.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mapUpload.get("secure_url").toString();
            articleDTO.setArticleImage(urlImage);
            articleDTO.setPublishedAt(LocalDate.now());
            articleService.saveArticle(articleDTO, profileId);
            return new ResponseEntity<>(articleDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Article>> getArticlesByAuthor(@PathVariable Long authorId) {
        List<Article> articles = (List<Article>) articleService.getArticleByAuthor(authorId);
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }


    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticlesById(@PathVariable Long articleId) {
        Article article =  articleService.getArticleById(articleId);

        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/findBy")
    public ResponseEntity<?> getArticleByTitle( @RequestParam String title){
        try{
            List<Article> articleList = articleService.getArticleByTitle(title);
            return new ResponseEntity<>(articleList, HttpStatus.OK);
        }catch (ProfileNotFound p){
            return new ResponseEntity<>("None article found!", HttpStatus.BAD_REQUEST);
        }
    }


}
