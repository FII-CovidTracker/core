package com.example.demo.controllers;

import com.example.demo.dto.RegionDto;
import com.example.demo.helpers.RegionMocker;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RegionControllerIt {

    private static RestTemplate restTemplate;
    private static final String BIG_ID = "500";
    private static final String SMALL_ID = "9";

    @Autowired
    RegionRepository regionRepository;

    @BeforeEach
    void init() {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8085"));

        RegionMocker.addMockedRegions(regionRepository);
    }

    @Test
    void getAllRegionsTest_shouldReturnAll() {
        List<RegionDto> regionDtos = restTemplate.getForObject("/region", List.class);
        assertThat(regionDtos).isNotEmpty();
    }

    @Test
    void saveOneRegionShouldSaveOne() {
        RegionDto givenRegionDto = getRegionDto("name", false);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<RegionDto> responseEntity = restTemplate.exchange("/region", HttpMethod.POST, new HttpEntity<>(givenRegionDto, headers), RegionDto.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    void deleteRegionById_shouldThrowEntityNotFound_WhenIdIs500() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange(String.format("/region/%s", BIG_ID), HttpMethod.DELETE, request, Void.class), HttpClientErrorException.class);
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteRegionById_shouldThrowNoContent_WhenIdIs9() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange(String.format("/region/%s", SMALL_ID), HttpMethod.DELETE, request, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    void findById_shouldThrowOK_WhenIdIs1() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<RegionDto> response = restTemplate.exchange("/region/1", HttpMethod.GET, request, RegionDto.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findById_shouldThrowNotFound_WhenIdIs500() {
        HttpEntity<Void> request = new HttpEntity<>(null);
        HttpClientErrorException exception = Assertions.catchThrowableOfType(() ->
                restTemplate.exchange(String.format("/region/%s", BIG_ID), HttpMethod.GET, request, Void.class), HttpClientErrorException.class);
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    private RegionDto getRegionDto(String name, boolean isGlobal) {
        return RegionDto.builder()
                .name(name)
                .isGlobal(isGlobal)
                .build();
    }
}
