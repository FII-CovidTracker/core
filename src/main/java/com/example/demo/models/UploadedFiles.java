package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ctk_uploaded_files")
public class UploadedFiles {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="String")
    private String url;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
