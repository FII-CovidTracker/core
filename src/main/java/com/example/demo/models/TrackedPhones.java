package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ctk_tracked_phones")
public class TrackedPhones {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "bluetoothId",unique = true)
    private String bluetoothId;
}
