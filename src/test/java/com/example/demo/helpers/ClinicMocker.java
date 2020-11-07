package com.example.demo.helpers;

import com.example.demo.models.Clinic;
import com.example.demo.models.User;
import com.example.demo.repositories.ClinicRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.LinkedList;
import java.util.List;

@TestComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClinicMocker {
    private static Clinic getMockedClinic() {
        Clinic clinic = new Clinic();
        clinic.setName("MC Clinic");
        clinic.setAddress("MC Address");
        clinic.setEmail("email@mail.com");
        clinic.setPhoneNumber("0123456789");
        clinic.setCui("1as2");
        return clinic;
    }

    public static void addMockedClinics(ClinicRepository repository) {
        List<Clinic> clinics = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            clinics.add(getMockedClinic());
        }
        repository.saveAll(clinics);
    }
}
