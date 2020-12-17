package com.example.demo.services;

import com.example.demo.dto.ArticleDto;
import com.example.demo.models.Article;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void articleDtoToArticleTest() {
//        String givenAuthor = "author";
//        String givenTitle = "title";
//        String givenMarkdown = "markdown";
//        LocalDate givenDate = LocalDate.of(2020, Month.NOVEMBER, 10);
//        ArticleDto givenArticleDto = getArticleDto(givenAuthor, givenTitle, givenMarkdown, givenDate);
//        Article expectedArticle = getArticle(givenAuthor, givenTitle, givenMarkdown, givenDate);
//
//        Article actualArticle = articleService.articleDtoToArticle(givenArticleDto);
//        assertThat(expectedArticle).isEqualToComparingOnlyGivenFields(actualArticle, "authorName", "title", "markdownContent", "publishDate");
//    }
//
//    @Test
//    public void articleToArticleDtoTest() {
//        String givenAuthor = "author";
//        String givenTitle = "title";
//        String givenMarkdown = "markdown";
//        LocalDate givenDate = LocalDate.of(2020, Month.NOVEMBER, 10);
//        ArticleDto expectedArticleDto = getArticleDto(givenAuthor, givenTitle, givenMarkdown, givenDate);
//        Article givenArticle = getArticle(givenAuthor, givenTitle, givenMarkdown, givenDate);
//
//        ArticleDto actualArticleDto = articleService.articleToArticleDto(givenArticle);
//        assertThat(expectedArticleDto).isEqualToComparingOnlyGivenFields(actualArticleDto, "author", "title", "markdownContent", "publishDate");
//
//    }

    private Article getArticle(String author, String title, String markdown, LocalDate date) {
        Article article = new Article();
        article.setAuthorName(author);
        article.setTitle(title);
        article.setMarkdownContent(markdown);
        article.setPublishDate(date);
        return article;
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
