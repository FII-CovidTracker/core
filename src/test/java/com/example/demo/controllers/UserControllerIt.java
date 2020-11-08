package com.example.demo.controllers;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.helpers.UserMocker;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerIt {
    private static RestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));

        UserMocker.addMockedUsers(userRepository);
    }

    @Test
    public void getAllTest_shouldReturnAll() {
        List<ArticleDto> articleDtos = restTemplate.getForObject("/user", List.class);
        assertThat(articleDtos).isNotEmpty();
    }

    @Test
    public void saveOneShouldSaveOne() {
        UserDto givenUserDto = getOtherUserDto("email", "name", "password");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<UserDto> responseEntity = restTemplate.exchange("/user", HttpMethod.POST, new HttpEntity<>(givenUserDto, headers), UserDto.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    public void deleteUserById_shouldThrowEntityNotFound_WhenIdIs500(){
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange("/user/500", HttpMethod.DELETE, request, Void.class), HttpClientErrorException.class);
//        System.out.println(exception.getLocalizedMessage());
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteUserById_shouldThrowNoContent_WhenIdIs9() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange("/user/9", HttpMethod.DELETE, request, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    private UserDto getOtherUserDto(String email, String name, String password) {
        return UserDto.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
