package com.example.demo.models;


import com.example.demo.enums.UserTypeEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ctk_user_type")
public class UserType {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;

    @OneToMany(mappedBy = "userType")
    private Set<User> users = new HashSet<>();

}
