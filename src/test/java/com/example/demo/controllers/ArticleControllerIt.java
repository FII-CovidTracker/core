package com.example.demo.controllers;

import com.example.demo.dto.ArticleDto;
import com.example.demo.helpers.ArticleMocker;
import com.example.demo.models.Authority;
import com.example.demo.repositories.ArticleRepository;
import com.example.demo.repositories.AuthorityRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ArticleControllerIt {
    private static final String BIG_ID = "500";
    private static final String URL = "http://localhost:8085";

    private static RestTemplate restTemplate;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    AuthorityRepository authorityRepository;


    @BeforeEach
    public void init() {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(URL));

        Authority givenAuthority = new Authority();
        authorityRepository.save(givenAuthority);

        ArticleMocker.addMockedArticles(articleRepository, givenAuthority.getId());
    }

    @Test
    public void getAllTest_shouldReturnAllTests() {
        List<ArticleDto> articleDtos = restTemplate.getForObject("/article", List.class);
        assertThat(articleDtos).isNotEmpty();
    }

    @Test
    public void saveOneShouldSaveOne() {
        ArticleDto givenArticleDto = getArticleDto("author", "title", "markdown", "10/11/2020");
        givenArticleDto.setAuthority_id(authorityRepository.findAll().get(0).getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ArticleDto> responseEntity = restTemplate.exchange("/article", HttpMethod.POST, new HttpEntity<>(givenArticleDto, headers), ArticleDto.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    public void deleteByIdShouldReturnNoContent() {
        HttpEntity<Void> request = new HttpEntity<>(null);

        ResponseEntity<Void> response = restTemplate.exchange(String.format("/article/%s", articleRepository.findAll().get(0).getId()), HttpMethod.DELETE, request, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void getByIdShouldReturnNotFound() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange(String.format("/article/%s", BIG_ID), HttpMethod.GET, request, Void.class), HttpClientErrorException.class);
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getByRegionShouldReturnEmptyList() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/article/getByRegion",URL))
                .queryParam("region","Iasi");
        ResponseEntity<List> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(null), List.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    private ArticleDto getArticleDto(String author, String title, String markdown, String date) {
        return ArticleDto.builder()
                .author(author)
                .title(title)
                .markdownContent(markdown)
                .publishDate(date)
                .authority_id(1L)
                .build();
    }


}
