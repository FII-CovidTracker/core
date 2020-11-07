package com.example.demo.controllers;

import com.example.demo.dto.ArticleDto;
import com.example.demo.helpers.ArticleMocker;
import com.example.demo.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ArticleControllerIt {
    private static RestTemplate restTemplate;
    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    public void init() {

        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));


     ArticleMocker.addMockedArticles(articleRepository);
        System.out.println(articleRepository.findAll().size());
    }

    @Test
    public void getAllTest_shouldReturnAllTests(){
        List<ArticleDto> articleDtos = restTemplate.getForObject("/article", List.class);
        assertThat(articleDtos).isNotEmpty();
    }
}
