package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ctk_article")
public class Article {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    private String authorName;

    @Column(name = "title")
    private String title;

    @Column(name = "markdown_content")
    private String markdownContent;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
}
