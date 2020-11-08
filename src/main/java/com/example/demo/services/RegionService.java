package com.example.demo.services;


import com.example.demo.Exceptions.EntityNotFoundException;
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

    public RegionDto regionToRegionDto(Region region) {
        return RegionDto.builder()
                .isGlobal(region.isGlobal())
                .name(region.getName())
                .build();
    }

    public List<RegionDto> findAll() {
        return regionRepository.findAll().stream()
                .map(region -> regionToRegionDto(region))
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteById(Long id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Region", id));
        regionRepository.delete(region);
    }

    public RegionDto findById(Long id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Region", id));
        return regionToRegionDto(region);

    }
}
