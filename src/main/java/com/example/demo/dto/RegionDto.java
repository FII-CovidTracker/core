package com.example.demo.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionDto {
    private long id;
    private String name;
    private boolean isGlobal;
}
