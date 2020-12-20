package com.example.demo.controllers;

import com.example.demo.dto.RegionDto;
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

import java.util.Arrays;
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
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8085"));

        UserMocker.addMockedUsers(userRepository);
    }

    @Test
    public void getAllTest_shouldReturnAll() {
        List<UserDto> userDtos = restTemplate.getForObject("/user", List.class);
        System.out.println(Arrays.toString(userDtos.toArray()));
        assertThat(userDtos).isNotEmpty();
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
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteUserById_shouldThrowNoContent_WhenIdIs198() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange("/user/198", HttpMethod.DELETE, request, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void findById_shouldThrowOK_WhenIdIs199() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<UserDto> response = restTemplate.exchange("/user/199", HttpMethod.GET, request, UserDto.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private UserDto getOtherUserDto(String email, String name, String password) {
        return UserDto.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
