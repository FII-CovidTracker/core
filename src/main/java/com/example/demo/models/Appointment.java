package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="ctk_appointment")
public class Appointment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "mobile_user_id")
    private MobileUser mobileUser;


    @ManyToOne()
    @JoinColumn(name = "status_id")
    private StatusType statusType;

    @OneToOne
    private UploadedFiles uploadedFiles;

}
