package com.example.demo.controllers;

import com.example.demo.dto.ClinicDto;
import com.example.demo.services.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "clinic")
public class ClinicsController {
    @Autowired
    ClinicService clinicService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClinicDto> getAll() {
        return clinicService.findAll();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicDto save(@RequestBody ClinicDto clinicDto) {
        clinicService.saveClinic(clinicDto);
        return clinicDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Valid @Min(1) Long id) {
        clinicService.deleteById(id);
    }
}
