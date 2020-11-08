package com.example.demo.controllers;


import com.example.demo.dto.ArticleDto;
import com.example.demo.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getAll() {
        return articleService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleDto save(@RequestBody ArticleDto articleDto) {
        articleService.saveArticle(articleDto);
        return articleDto;
    }
}
