package com.example.projectlombok.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private String title;

    private Integer yearPublished;

    private Author author;
}
