package com.example.demo.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String password;

}
