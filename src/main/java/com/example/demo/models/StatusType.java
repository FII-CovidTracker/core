package com.example.demo.models;

import com.example.demo.enums.StatusTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ctk_status_type")
public class StatusType {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum statusTypeEnum;

    @OneToMany(mappedBy = "statusType")
    private Set<Appointment> appointments = new HashSet<>();
}
