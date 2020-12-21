package com.example.demo.services;

import com.example.demo.dto.ArticleDto;
import com.example.demo.helpers.ServiceTestValuesWrapper;
import com.example.demo.models.Article;
import com.example.demo.models.Authority;
import com.example.demo.models.Clinic;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleServiceTest {
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
    public void deleteTestShouldCallDeleteFromRepository() {
        Article article = getArticle();

        doAnswer(iom -> Optional.of(article))
                .when(articleRepository)
                .findById(any());

        doAnswer(iom -> null)
                .when(articleRepository)
                .delete(any());

        articleService.deleteById(article.getId());

        verify(articleRepository, atLeastOnce()).delete(any());
    }

    @Test
    public void findByIdTestShouldCallFindByIdFromRepository() {
        Article article = getArticle();

        doAnswer(iom -> Optional.of(article))
                .when(articleRepository)
                .findById(any());

        articleService.findById(article.getId());

        verify(articleRepository, atLeastOnce()).findById(any());
    }

    @Test
    public void findByRegionTestShouldCallLocalArticlesRepository() {
        Article article = getArticle();

        doAnswer(iom -> List.of(article))
                .when(articleRepository)
                .localArticles(any());


        articleService.findByRegion(ServiceTestValuesWrapper.REGION_NAME);

        verify(articleRepository, atLeastOnce()).localArticles(any());
    }

    @Test
    public void globalFindByRegionTestShouldCallFindAllRepository() {
        Article article = getArticle();

        doAnswer(iom -> List.of(article))
                .when(articleRepository)
                .localArticles(ServiceTestValuesWrapper.GLOBAL_REGION_NAME);

        doAnswer(iom -> List.of(article))
                .when(articleRepository)
                .findAll();

        articleService.findByRegion(ServiceTestValuesWrapper.GLOBAL_REGION_NAME);

        verify(articleRepository, atLeastOnce()).findAll();
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
        expectedArticleDto.setPublishDate(ServiceTestValuesWrapper.PUBLISH_DATE.toString());
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
        article.setId(1L);
        article.setAuthorName(ServiceTestValuesWrapper.AUTHOR_NAME);
        article.setTitle(ServiceTestValuesWrapper.ARTICLE_TITLE);
        article.setMarkdownContent(ServiceTestValuesWrapper.MARKDOWN_CONTENT);
        article.setPublishDate(ServiceTestValuesWrapper.PUBLISH_DATE);
        article.setAuthority(getAuthority());
        return article;
    }

    private ArticleDto getArticleDto() {
        return ArticleDto.builder()
                .author(ServiceTestValuesWrapper.AUTHOR_NAME)
                .title(ServiceTestValuesWrapper.ARTICLE_TITLE)
                .markdownContent(ServiceTestValuesWrapper.MARKDOWN_CONTENT)
                .publishDate(ServiceTestValuesWrapper.DATE_TIME_FORMATTER.format(ServiceTestValuesWrapper.PUBLISH_DATE))
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
