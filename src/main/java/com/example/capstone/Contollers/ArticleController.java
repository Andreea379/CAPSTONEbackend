package com.example.capstone.Contollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.capstone.Exceptions.ProfileNotFound;
import com.example.capstone.Models.Article;
import com.example.capstone.Models.Profile;
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
import java.util.Optional;

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

    @PostMapping("/newArticle/{userId}")
    public ResponseEntity<?> newArticle(@RequestPart("article") @Validated ArticleDTO articleDTO, BindingResult validation, @PathVariable Long userId, @RequestPart(value = "articleImage") MultipartFile articleImage){
        if(validation.hasErrors()){
            StringBuilder message = new StringBuilder("Problems with the validation: \n");

            for(ObjectError error : validation.getAllErrors()){
                message.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        }
        try{
            Profile profile = profileService.getProfileByUserId(userId);

            Article article = articleService.fromArticleDTOtoArticle(articleDTO);
            article.setAuthor(profile);
            article.setPublishedAt(LocalDate.now());

            Map mapUpload = cloudinary.uploader().upload(articleImage.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mapUpload.get("secure_url").toString();
            articleDTO.setArticleImage(urlImage);
            articleService.saveArticle(article);
            return new ResponseEntity<>("Article created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/{articleId}")
    public ResponseEntity<?> newArticle( @PathVariable Long articleId,@RequestPart("article") @Validated ArticleDTO articleDTO, BindingResult validation){
        if(validation.hasErrors()){
            StringBuilder message = new StringBuilder("Problems with the validation: \n");

            for(ObjectError error : validation.getAllErrors()){
                message.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        }
        try{
            List<Article> article = articleRepository.findByArticleId(articleId);
            Article updateArticle = articleService.updateArticle(articleDTO, articleId);
            return new ResponseEntity<>("Article updated", HttpStatus.CREATED);
        }catch (ProfileNotFound e){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }
}
