package com.example.demo.repositories;

import com.example.demo.dto.ArticleDto;
import com.example.demo.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAll();

    @Query(value = "select ar from Article ar join Authority au on ar.authority.id = au.id join Region re on au.region.id = re.id where re.name = ?1 or re.isGlobal = true",
    nativeQuery = false)
    List<Article> localArticles(String regionName);
}
