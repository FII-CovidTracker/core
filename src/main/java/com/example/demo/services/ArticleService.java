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

    private ArticleDto articleToArticleDto(Article article) {
        return ArticleDto.builder()
                .author(article.getAuthorName())
                .localDate(article.getPublishDate())
                .markDownContent(article.getMarkdownContent())
                .title(article.getTitle())
                .build();
    }
}
