package com.example.demo.services;

import com.example.demo.dto.ClinicDto;
import com.example.demo.models.Clinic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClinicServiceTest {

    @Autowired
    private ClinicService clinicService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void clinicDtoToClinicTest() {
        String givenName = "name";
        String givenAddress = "address";
        String givenEmail = "email";
        String givenPhoneNumber = "phoneNumber";
        String givenCUI = "CUI";
        ClinicDto givenClinicDto = getClinicDto(givenName, givenAddress, givenEmail, givenPhoneNumber, givenCUI);
        Clinic expectedClinic = getClinic(givenName, givenAddress, givenEmail, givenPhoneNumber, givenCUI);

        Clinic actualClinic = clinicService.clinicDtoToClinic(givenClinicDto);
        assertThat(expectedClinic).isEqualToComparingOnlyGivenFields(actualClinic,
                "name", "address", "email", "phoneNumber", "cui");

    }

    @Test
    public void clinicToClinicDtoTest() {
        String givenName = "name";
        String givenAddress = "address";
        String givenEmail = "email";
        String givenPhoneNumber = "phoneNumber";
        String givenCUI = "CUI";
        ClinicDto expectedClinicDto = getClinicDto(givenName, givenAddress, givenEmail, givenPhoneNumber, givenCUI);
        Clinic givenClinic = getClinic(givenName, givenAddress, givenEmail, givenPhoneNumber, givenCUI);

        ClinicDto actualClinicDto = clinicService.clinicToClinicDto(givenClinic);
        assertThat(expectedClinicDto).isEqualToComparingOnlyGivenFields(actualClinicDto,
                "name", "address", "email", "phoneNumber", "cui");

    }

    private Clinic getClinic(String name, String address, String email, String phoneNumber, String cui) {
        Clinic clinic = new Clinic();
        clinic.setName(name);
        clinic.setAddress(address);
        clinic.setEmail(email);
        clinic.setPhoneNumber(phoneNumber);
        clinic.setCui(cui);
        return clinic;
    }

    private ClinicDto getClinicDto(String name, String address, String email, String phoneNumber, String cui) {
        return ClinicDto.builder()
                .name(name)
                .address(address)
                .email(email)
                .phoneNumber(phoneNumber)
                .cui(cui)
                .build();
    }
}

