package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ClinicDto {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private String cui;
}
