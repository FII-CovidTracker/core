package com.example.demo.controllers;

import com.example.demo.dto.ArticleDto;
import com.example.demo.helpers.ArticleMocker;
import com.example.demo.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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
    }

    @Test
    public void getAllTest_shouldReturnAllTests(){
        List<ArticleDto> articleDtos = restTemplate.getForObject("/article", List.class);
        assertThat(articleDtos).isNotEmpty();
    }

    @Test
    public void saveOneShouldSaveOne() {
        ArticleDto givenArticleDto = getArticleDto("author", "title", "markdown", "10/11/2020");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ArticleDto> responseEntity = restTemplate.exchange("/article", HttpMethod.POST, new HttpEntity<>(givenArticleDto, headers), ArticleDto.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

    }

    private ArticleDto getArticleDto(String author, String title, String markdown, String date) {
        return ArticleDto.builder()
                .author(author)
                .title(title)
                .markdownContent(markdown)
                .publishDate(date)
                .build();
    }


}
