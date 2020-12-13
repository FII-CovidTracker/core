package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ArticleDto {
    private long id;
    private String author;
    private String title;
    private String markdownContent;
    private String publishDate;
    private long authority_id;
}
