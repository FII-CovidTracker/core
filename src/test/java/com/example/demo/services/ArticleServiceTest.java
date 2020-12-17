package com.example.demo.services;

import com.example.demo.dto.ArticleDto;
import com.example.demo.models.Article;
import com.example.demo.models.Authority;
import com.example.demo.repositories.ArticleRepository;
import org.hibernate.mapping.Any;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleServiceTest {
    private static final String AUTHOR_NAME = "AUTHOR";
    private static final String ARTICLE_TITLE = "TITLE";
    private static final String MARKDOWN_CONTENT = "MARKDOWN";
    private static final LocalDate PUBLISH_DATE = LocalDate.of(2020, Month.NOVEMBER, 10);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");

    private ArticleRepository articleRepository;
    private AuthorityService authorityService;

    @Autowired
    private ArticleService articleService;

    public ArticleServiceTest() {

        articleRepository = Mockito.mock(ArticleRepository.class, this::getError);
        authorityService = Mockito.mock(AuthorityService.class, this::getError);
        articleService = new ArticleService(
                articleRepository,
                authorityService);
    }

    @Test
    public void findAllShouldReturnOneElement() {
        Article expectedArticle = getArticle();
        Authority givenAuthority = new Authority();

        givenAuthority.setId(1L);
        expectedArticle.setId(1L);
        expectedArticle.setAuthority(givenAuthority);

        doAnswer(iom -> Arrays.asList(expectedArticle, expectedArticle))
                .when(articleRepository)
                .findAll();

        assertThat(articleService.findAll().size()).isEqualTo(2);
    }

    @Test
    public void articleDtoToArticleShouldReturnExpectedArticleTest() {
        ArticleDto givenArticleDto = getArticleDto();
        Article expectedArticle = getArticle();
        givenArticleDto.setAuthority_id(1L);

        doAnswer(iom -> new Authority())
                .when(authorityService)
                .findAuthorityById(1L);

        Article actualArticle = articleService.articleDtoToArticle(givenArticleDto);
        assertThat(expectedArticle).isEqualToComparingOnlyGivenFields(actualArticle, "authorName", "title", "markdownContent", "publishDate");
    }

    @Test
    public void articleToArticleDtoTestShouldReturnExpectedArticleDto() {
        ArticleDto expectedArticleDto = getArticleDto();
        expectedArticleDto.setPublishDate(PUBLISH_DATE.toString());
        Article givenArticle = getArticle();
        givenArticle.setId(1L);
        givenArticle.setAuthority(getAuthority());

        ArticleDto actualArticleDto = articleService.articleToArticleDto(givenArticle);
        assertThat(expectedArticleDto).isEqualToComparingOnlyGivenFields(actualArticleDto, "author", "title", "markdownContent", "publishDate");

    }

    @Test
    public void saveTestShouldCallSaveFromRepository() {
        ArticleDto givenArticleDto = getArticleDto();

        doAnswer(iom -> new Authority())
                .when(authorityService)
                .findAuthorityById(anyLong());

        doAnswer(iom -> new Article())
                .when(articleRepository)
                .save(any());

        articleService.saveArticle(givenArticleDto);

        verify(articleRepository, atLeastOnce()).save(any());
    }

    private Article getArticle() {
        Article article = new Article();
        article.setAuthorName(AUTHOR_NAME);
        article.setTitle(ARTICLE_TITLE);
        article.setMarkdownContent(MARKDOWN_CONTENT);
        article.setPublishDate(PUBLISH_DATE);
        return article;
    }

    private ArticleDto getArticleDto() {
        return ArticleDto.builder()
                .author(AUTHOR_NAME)
                .title(ARTICLE_TITLE)
                .markdownContent(MARKDOWN_CONTENT)
                .publishDate(DATE_TIME_FORMATTER.format(PUBLISH_DATE))
                .build();
    }

    private Authority getAuthority() {
        Authority newAuthority = new Authority();
        newAuthority.setId(1L);
        return newAuthority;
    }

    private Object getError(InvocationOnMock invocationOnMock) {
        throw new RuntimeException(String.format("Should not reach %s::%s", invocationOnMock.getMock().getClass().getName(),
                invocationOnMock.getMethod().getName()));
    }
}
