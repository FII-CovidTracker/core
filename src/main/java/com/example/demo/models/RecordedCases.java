package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="ctk_recorded_cases")
public class RecordedCases {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "verified")
    private boolean isVerified;

    @OneToOne
    private UploadedFiles proof;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToOne
    private TrackedPhones phone;
}
