package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ctk_authority")
public class Authority {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "can_verify_cases")
    private Boolean canVerifyCases;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "hashedPassword")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "authority")
    private Set<Article> articleSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "authority")
    private Set<UploadedFiles> uploadedFiles = new HashSet<>();


    @OneToMany(mappedBy = "authority")
    private Set<AuthorityUser> authorityUsers = new HashSet<>();

}
