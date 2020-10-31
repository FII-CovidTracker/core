package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="ctk_authority_user")
public class AuthorityUser {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
}
