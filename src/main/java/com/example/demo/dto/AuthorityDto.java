package com.example.demo.dto;


import com.example.demo.models.Region;
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
    private String password;
    private String email;
    private String phoneNumber;
    private long region_id;
}
