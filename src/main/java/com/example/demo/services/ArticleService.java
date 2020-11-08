package com.example.demo.services;

import com.example.demo.dto.ArticleDto;
import com.example.demo.models.Article;
import com.example.demo.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(article -> articleToArticleDto(article))
                .collect(Collectors.toList());
    }

    public void saveArticle(ArticleDto articleDto) {
        Article article = articleDtoToArticle(articleDto);
        articleRepository.save(article);
    }

    public ArticleDto articleToArticleDto(Article article) {
        return ArticleDto.builder()
                .author(article.getAuthorName())
                .publishDate(article.getPublishDate())
                .markdownContent(article.getMarkdownContent())
                .title(article.getTitle())
                .build();

    }

    public Article articleDtoToArticle(ArticleDto articleDto) {
        Article article = new Article();
        article.setAuthorName(articleDto.getAuthor());
        article.setTitle(articleDto.getTitle());
        article.setMarkdownContent(articleDto.getMarkdownContent());
        article.setPublishDate(articleDto.getPublishDate());
        return article;
    }
}
