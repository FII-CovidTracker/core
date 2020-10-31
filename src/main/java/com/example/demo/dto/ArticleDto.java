package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleDto {
    private String author;
    private String title;
    private String markDownContent;
    private LocalDate localDate;
}
