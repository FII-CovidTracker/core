package com.example.demo.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionDto {
    private long id;
    private String name;
    private boolean isGlobal;
}
