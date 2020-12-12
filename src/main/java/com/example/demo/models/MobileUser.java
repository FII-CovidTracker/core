package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ctk_mobile_user")
public class MobileUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "mobileUser")
    private Set<Appointment> appointments = new HashSet<>();
}
