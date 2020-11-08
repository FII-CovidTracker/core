package com.example.demo.controllers;

import com.example.demo.dto.AuthorityDto;
import com.example.demo.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Valid Long id) {
        authorityService.deleteById(id);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorityDto getById(@PathVariable @Valid Long id) {
        return authorityService.findById(id);
    }
}
