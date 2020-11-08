package com.example.demo.controllers;


import com.example.demo.dto.RegionDto;
import com.example.demo.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Valid  Long id) {
        regionService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RegionDto getById(@PathVariable @Valid Long id) {
        return regionService.findById(id);
    }
}
