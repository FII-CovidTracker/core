package com.example.demo.controllers;

import com.example.demo.dto.AuthorityDto;
import com.example.demo.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "authority")
public class AuthorityController {
    @Autowired
    AuthorityService authorityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorityDto> getAll() {
        return authorityService.findAll();
    }

}
