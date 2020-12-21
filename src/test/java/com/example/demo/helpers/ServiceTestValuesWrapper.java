package com.example.demo.helpers;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class ServiceTestValuesWrapper {

    public ServiceTestValuesWrapper () {

    }

    public static final String REGION_NAME = "IASI";
    public static final String AUTHOR_NAME = "AUTHOR";
    public static final String ARTICLE_TITLE = "TITLE";
    public static final String GLOBAL_REGION_NAME = "global";
    public static final String MARKDOWN_CONTENT = "MARKDOWN";
    public static final LocalDate PUBLISH_DATE = LocalDate.of(2020, Month.NOVEMBER, 10);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");
    public static final String AUTHORITY_NAME = "Name";
    public static final String PASSWORD = "test";
    public static final String ADDRESS = "Strada stada cea strada, numarul numar, apartamentul appartament";
    public static final String PHONE = "07777777777";
    public static final String EMAIL = "test@test.com";
    public static final String CUI = "12345678990";
}
