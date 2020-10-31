package com.example.demo.services;


import com.example.demo.dto.RegionDto;
import com.example.demo.models.Region;
import com.example.demo.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    private RegionDto regionToRegionDto(Region region){
        return RegionDto.builder()
                .isGlobal(region.isGlobal())
                .name(region.getName())
                .build();
    }

    public List<RegionDto> findAll(){
        return regionRepository.findAll().stream()
                .map(region -> regionToRegionDto(region))
                .collect(Collectors.toUnmodifiableList());
    }
}
