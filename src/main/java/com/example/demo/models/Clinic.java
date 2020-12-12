package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "ctk_clinic")
public class Clinic {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name ="email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "CUI")
    private String cui;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "clinic")
    private Set<UploadedFiles> uploadedFiles = new HashSet<>();

    @OneToMany(mappedBy = "clinic")
    private Set<ClinicUser> clinicUsers = new HashSet<>();


    @OneToMany(mappedBy = "clinic")
    private Set<Appointment> appointments = new HashSet<>();
}
