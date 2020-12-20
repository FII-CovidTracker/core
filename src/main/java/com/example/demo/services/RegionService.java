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

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public RegionDto regionToRegionDto(Region region) {
        return RegionDto.builder()
                .id(region.getId())
                .isGlobal(region.isGlobal())
                .name(region.getName())
                .build();
    }

    public Region regionDtoToRegion(RegionDto regionDto) {
        Region region = new Region();
        region.setName(regionDto.getName());
        region.setGlobal(regionDto.isGlobal());
        return region;
    }

    public List<RegionDto> findAll() {
        return regionRepository.findAll().stream()
                .map(region -> regionToRegionDto(region))
                .collect(Collectors.toUnmodifiableList());
    }

    public void saveRegion(RegionDto regionDto) {
        Region region = regionDtoToRegion(regionDto);
        System.out.println(region.getName());
        regionRepository.save(region);
    }
    
    public void deleteById(Long id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Region", id));
        regionRepository.delete(region);
    }

    public RegionDto findById(Long id) {
        return regionToRegionDto(findRegionById(id));
    }

    public Region findRegionById(Long id) {
        return regionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Region", id));
    }
}
