package com.example.projectlombok;

import com.example.projectlombok.domain.Author;
import com.example.projectlombok.domain.Book;

public class TestData {
    private TestData() {

    }

    public static Author createTestAuthor() {
        return Author.builder()
                .givenName("Shubham")
                .FamilyName("Salaskar")
                .age(22)
                .build();
    }

    public static Book craeteTestBook() {
        return Book.builder()
                .title("Shades of Lust")
                .yearPublished(2003)
                .author(createTestAuthor())
                .build();
    }
}
