package com.example.demo;

import com.example.demo.dto.ArticleDto;
import com.example.demo.repositories.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoApplicationTests {
	private RestTemplate restTemplate;
	@Autowired
	ArticleRepository articleRepository;

	@Before
	public void init() {

		restTemplate = new RestTemplate();
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));
	}

	@Test
	void getAll_shouldNotReturnAnEmptyList() {
		System.out.println(articleRepository.findAll());

		List<ArticleDto> disciplines = restTemplate.getForObject("/article", List.class);
		Assertions.assertThat(disciplines.size()).isZero();
	}
}
