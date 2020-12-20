package com.example.demo.services;

import com.example.demo.dto.ClinicDto;
import com.example.demo.dto.ClinicDto;
import com.example.demo.models.Clinic;
import com.example.demo.models.Authority;
import com.example.demo.models.Clinic;
import com.example.demo.models.Region;
import com.example.demo.repositories.ClinicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ClinicServiceTest {
    private static final String ADDRESS = "Strada stada cea strada, numarul numar, apartamentul appartament";
    private static final String PHONE = "07777777777";
    private static final String CUI = "12345678990";
    private static final String EMAIL = "email@adresa.com";

    private ClinicRepository clinicRepository;

    @Autowired
    private ClinicService clinicService;

    public ClinicServiceTest() {

        clinicRepository = Mockito.mock(ClinicRepository.class, this::getError);
        clinicService = new ClinicService(clinicRepository);
    }

    @Test
    public void findAllShouldReturnOneElement() {
        Clinic expectedClinic = getClinic();

        expectedClinic.setId(1L);

        doAnswer(iom -> Arrays.asList(expectedClinic, expectedClinic))
                .when(clinicRepository)
                .findAll();

        assertThat(clinicService.findAll().size()).isEqualTo(2);
    }

    @Test
    public void saveTestShouldCallSaveFromRepository() {
        ClinicDto givenClinicDto = getClinicDto();

        doAnswer(iom -> new Clinic())
                .when(clinicRepository)
                .save(any());

        clinicService.saveClinic(givenClinicDto);

        verify(clinicRepository, atLeastOnce()).save(any());
    }

    @Test
    public void deleteTestShouldCallDeleteFromRepository() {
        Clinic clinic = getClinic();

        doAnswer(iom -> Optional.of(clinic))
                .when(clinicRepository)
                .findById(any());

        doAnswer(iom -> null)
                .when(clinicRepository)
                .delete(any());

        clinicService.deleteById(clinic.getId());

        verify(clinicRepository, atLeastOnce()).delete(any());
    }

    @Test
    public void findByIdTestShouldCallFindByIdFromRepository() {
        Clinic clinic = getClinic();

        doAnswer(iom -> Optional.of(clinic))
                .when(clinicRepository)
                .findById(any());

        clinicService.findById(clinic.getId());

        verify(clinicRepository, atLeastOnce()).findById(any());
    }

    private Clinic getClinic() {
        Clinic clinic = new Clinic();
        clinic.setId(1L);
        clinic.setAddress(ADDRESS);
        clinic.setCui(PHONE);
        clinic.setEmail(EMAIL);
        clinic.setPhoneNumber(CUI);
        clinic.setAppointments(new LinkedHashSet<>());
        clinic.setRegion(new Region());
        clinic.setClinicUsers(new LinkedHashSet<>());
        clinic.setUploadedFiles(new LinkedHashSet<>());
        return clinic;
    }

    private ClinicDto getClinicDto() {
        return ClinicDto.builder()
                .phoneNumber(PHONE)
                .address(ADDRESS)
                .email(EMAIL)
                .cui(CUI)
                .build();
    }

    private Object getError(InvocationOnMock invocationOnMock) {
        throw new RuntimeException(String.format("Should not reach %s::%s", invocationOnMock.getMock().getClass().getName(),
                invocationOnMock.getMethod().getName()));
    }
}

