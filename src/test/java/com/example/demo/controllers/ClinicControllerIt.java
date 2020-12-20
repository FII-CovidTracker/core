package com.example.demo.controllers;

import com.example.demo.dto.ClinicDto;
import com.example.demo.helpers.ClinicMocker;
import com.example.demo.repositories.ClinicRepository;
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
class ClinicControllerIt {

    private static RestTemplate restTemplate;
    private static final String BIG_ID = "500";
    private static final String SMALL_ID = "9";

    @Autowired
    ClinicRepository clinicRepository;

    @BeforeEach
    void init() {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8085"));

        ClinicMocker.addMockedClinics(clinicRepository);
    }

    @Test
    void getAllClinicsTest_shouldReturnAll() {
        List<ClinicDto> clinicDtos = restTemplate.getForObject("/clinic", List.class);
        assertThat(clinicDtos).isNotEmpty();
    }

    @Test
    void saveOneClinicShouldSaveOne() {
        ClinicDto givenClinicDto = getClinicDto("name", "address", "email", "phoneNumber", "cui");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ClinicDto> responseEntity = restTemplate.exchange("/clinic", HttpMethod.POST, new HttpEntity<>(givenClinicDto, headers), ClinicDto.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    void deleteClinicById_shouldThrowEntityNotFound_WhenIdIs500() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange(String.format("/clinic/%s", BIG_ID), HttpMethod.DELETE, request, Void.class), HttpClientErrorException.class);
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteClinicById_shouldThrowNoContent_WhenIdIs9() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange(String.format("/clinic/%s", SMALL_ID), HttpMethod.DELETE, request, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    void findById_shouldThrowOK_WhenIdIs1() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<ClinicDto> response = restTemplate.exchange("/clinic/1", HttpMethod.GET, request, ClinicDto.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findById_shouldThrowNotFound_WhenIdIs500() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange(String.format("/clinic/%s", BIG_ID), HttpMethod.GET, request, Void.class), HttpClientErrorException.class);
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    private ClinicDto getClinicDto(String name, String address, String email, String phoneNumber, String cui) {
        return ClinicDto.builder()
                .name(name)
                .address(address)
                .email(email)
                .phoneNumber(phoneNumber)
                .cui(cui)
                .build();
    }
}
