package com.example.demo.controllers;


import com.example.demo.dto.RegionDto;
import com.example.demo.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "region")
public class RegionController {
    @Autowired
    RegionService regionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RegionDto> getAll() {
        return regionService.findAll();
    }
}
