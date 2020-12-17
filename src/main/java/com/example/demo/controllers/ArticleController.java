package com.example.demo.controllers;


import com.example.demo.dto.ArticleDto;
import com.example.demo.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Valid Long id) {
        articleService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleDto getById(@PathVariable @Valid Long id) {
       return articleService.findById(id);
    }

    @GetMapping("/getByRegion")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getByRegion(@RequestParam("region") String regionName) {
        return articleService.findByRegion(regionName);
    }
}
