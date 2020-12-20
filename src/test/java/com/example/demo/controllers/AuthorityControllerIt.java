package com.example.demo.controllers;

import com.example.demo.dto.AuthorityDto;
import com.example.demo.dto.LoginCredidentials;
import com.example.demo.dto.LoginResult;
import com.example.demo.helpers.AuthorityMocker;
import com.example.demo.models.Region;
import com.example.demo.repositories.AuthorityRepository;
import com.example.demo.repositories.RegionRepository;
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
class AuthorityControllerIt {

    private static RestTemplate restTemplate;
    private static final String BIG_ID = "500";
    private static final String SMALL_ID = "132";
    private static final String validEmail = "test@test.com";
    private static final String validPassword = "test";

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RegionRepository regionRepository;


    @BeforeEach
    void init() {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8085"));

        Region testRegion = new Region();
        regionRepository.save(testRegion);
        AuthorityMocker.addMockedAuthorities(authorityRepository, testRegion);
    }

    @Test
    void getAllAuthoritiesTest_shouldReturnAll() {
        List<AuthorityDto> authorityDtos = restTemplate.getForObject("/authority", List.class);
        System.out.println(Arrays.toString(authorityDtos.toArray()));
        assertThat(authorityDtos).isNotEmpty();
    }

    @Test
    void saveOneAuthorityShouldSaveOne() {
        AuthorityDto givenAuthorityDto = getAuthorityDto("name", "address", false, "test2@test2.com", "0123456789", "test2");
        givenAuthorityDto.setRegion_id(regionRepository.findAll().get(0).getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<AuthorityDto> responseEntity = restTemplate.exchange("/authority", HttpMethod.POST, new HttpEntity<>(givenAuthorityDto, headers), AuthorityDto.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    void deleteAuthorityById_shouldThrowEntityNotFound_WhenIdIs500() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange(String.format("/authority/%s", BIG_ID), HttpMethod.DELETE, request, Void.class), HttpClientErrorException.class);
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteAuthorityById_shouldThrowNoContent_WhenIdIs132() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange(String.format("/authority/%s", SMALL_ID), HttpMethod.DELETE, request, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testLoginWithCorrectCredentials_respondsWithStatusOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AuthorityDto newAuthorityDto = getAuthorityDto("name", "address", false, "test@test.com", "0123456789", "test");
        newAuthorityDto.setRegion_id(regionRepository.findAll().get(0).getId());
        LoginCredidentials validLoginCredentials = new LoginCredidentials(validEmail, validPassword);
        ResponseEntity<AuthorityDto> postResponseEntity = restTemplate.exchange("/authority", HttpMethod.POST, new HttpEntity<>(newAuthorityDto, headers), AuthorityDto.class);
        ResponseEntity<LoginResult> responseEntity = restTemplate.exchange("/authority/login", HttpMethod.POST, new HttpEntity<>(validLoginCredentials, headers), LoginResult.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void findById_shouldThrowOK_WhenIdIs73() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<AuthorityDto> response = restTemplate.exchange("/authority/73", HttpMethod.GET, request, AuthorityDto.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findById_shouldThrowNotFound_WhenIdIs500() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange(String.format("/authority/%s", BIG_ID), HttpMethod.GET, request, Void.class), HttpClientErrorException.class);
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    private AuthorityDto getAuthorityDto(String name, String address, boolean canVerifyCases, String email, String phoneNumber, String password) {
        return AuthorityDto.builder()
                .name(name)
                .address(address)
                .canVerifyCases(canVerifyCases)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }
}
