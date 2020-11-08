package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ArticleDto {
    private String author;
    private String title;
    private String markdownContent;
    private LocalDate publishDate;
}
