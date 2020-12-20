package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.dto.ArticleDto;
import com.example.demo.models.Article;
import com.example.demo.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorityService authorityService;


    public ArticleService(ArticleRepository articleRepository, AuthorityService authorityService) {
        this.articleRepository = articleRepository;
        this.authorityService = authorityService;
    }

    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(article -> articleToArticleDto(article))
                .collect(Collectors.toList());
    }

    public void saveArticle(ArticleDto articleDto) {
        Article article = articleDtoToArticle(articleDto);
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        Article region = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article", id));
        articleRepository.delete(region);
    }

    public ArticleDto findById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article", id));
        return articleToArticleDto(article);

    }

    public List<ArticleDto> findByRegion(String regionName) {

        System.out.println(regionName);
        if (regionName.equals("global")) {
            return findAll();
        }
        else {
            return articleRepository.localArticles(regionName).stream()
                    .map(article -> (articleToArticleDto(article)))
                    .collect(Collectors.toList());
        }
    }

    public ArticleDto articleToArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .author(article.getAuthorName())
                .publishDate(article.getPublishDate().toString())
                .markdownContent(article.getMarkdownContent())
                .title(article.getTitle())
                .authority_id(article.getAuthority().getId())
                .build();
    }

    public Article articleDtoToArticle(ArticleDto articleDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Article article = new Article();
        article.setAuthorName(articleDto.getAuthor());
        article.setTitle(articleDto.getTitle());
        article.setMarkdownContent(articleDto.getMarkdownContent());
        LocalDate localDate = LocalDate.parse(articleDto.getPublishDate(), formatter);
        article.setPublishDate(localDate);
        article.setAuthority(authorityService.findAuthorityById(articleDto.getAuthority_id()));
        return article;
    }
}
