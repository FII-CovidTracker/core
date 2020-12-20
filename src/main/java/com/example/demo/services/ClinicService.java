package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.dto.ClinicDto;
import com.example.demo.models.Clinic;
import com.example.demo.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    @Autowired
    ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<ClinicDto> findAll() {
        return clinicRepository.findAll().stream()
                .map(clinic -> clinicToClinicDto(clinic))
                .collect(Collectors.toList());
    }

    public void saveClinic(ClinicDto clinicDto) {
        Clinic clinic = clinicDtoToClinic(clinicDto);
        clinicRepository.save(clinic);
    }

    public void deleteById(Long id) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Clinic", id));
        System.out.println(clinic);
        clinicRepository.delete(clinic);
    }

    public ClinicDto findById(Long id) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Clinic", id));
        return clinicToClinicDto(clinic);

    }
    
    public ClinicDto clinicToClinicDto(Clinic clinic) {
        return ClinicDto.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .address(clinic.getAddress())
                .email(clinic.getEmail())
                .phoneNumber(clinic.getPhoneNumber())
                .cui(clinic.getCui())
                .build();
    }

    public Clinic clinicDtoToClinic(ClinicDto clinicDto) {
        Clinic clinic = new Clinic();
        clinic.setName(clinicDto.getName());
        clinic.setAddress(clinicDto.getAddress());
        clinic.setEmail(clinicDto.getEmail());
        clinic.setPhoneNumber(clinicDto.getPhoneNumber());
        clinic.setCui(clinicDto.getCui());
        return clinic;
    }
    
    
}
