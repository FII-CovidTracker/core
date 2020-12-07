package com.example.demo.dto;


import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorityDto {
    private List<String> photoURL = new LinkedList<>();
    private String name;
    private Boolean canVerifyCases;
    private String address;
    private String hashedPassword;
    private String email;
    private String phoneNumber;
    private String region;
}
