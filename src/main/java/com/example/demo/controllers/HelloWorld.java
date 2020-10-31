package com.example.demo.controllers;

import com.example.demo.models.Region;
import com.example.demo.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path ="helloWorld")
public class HelloWorld {

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Region> getAll() {
        return regionRepository.findAll();
    }
}
