package com.example.demo.helpers;


import com.example.demo.models.Article;
import com.example.demo.models.Authority;
import com.example.demo.repositories.ArticleRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@TestComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArticleMocker {

    private static Long articleIndex = 1l;

    private static Authority getMockedAuthority(Long authorityId) {
        Authority authority = new Authority();
        authority.setId(authorityId);
        return authority;
    }

    private static Article getMockedArticle() {
        Article article = new Article();
        article.setAuthorName("Mihnea Rezmerita");
        article.setMarkdownContent("Random Content");
        article.setPublishDate(LocalDate.of(1999, 12, 13));
        article.setTitle("Awesome title");
        return article;
    }

    public static void addMockedArticles(ArticleRepository repository, Long authorityId) {
        Authority givenAuthority = getMockedAuthority(authorityId);
        List<Article> articles = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            Article article = getMockedArticle();
            article.setAuthority(givenAuthority);
            articles.add(article);
        }
        repository.saveAll(articles);
    }
}
