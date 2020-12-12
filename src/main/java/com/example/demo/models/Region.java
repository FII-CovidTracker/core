package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ctk_region")
public class Region {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "global")
    private boolean isGlobal;

    @OneToMany(mappedBy = "region")
    private Set<Authority> authoritySet = new HashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<Clinic> clinicSet = new HashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<RecordedCases> recordedCases = new HashSet<>();
}
