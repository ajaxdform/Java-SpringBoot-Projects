package com.jdbc_template.intracting_with_data;

import com.jdbc_template.intracting_with_data.domain.Author;
import com.jdbc_template.intracting_with_data.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {

    }

    public static Author createTesAuthorA() {
        return Author.builder()
            .id(1L)
            .name("Shubham Salaskar")
            .age(22)
            .build();
    }

    public static Author createTesAuthorB() {
        return Author.builder()
            .id(2L)
            .name("Vaibhav Shinde")
            .age(23)
            .build();
    }

    public static Author createTesAuthorC() {
        return Author.builder()
            .id(3L)
            .name("Harshita XX")
            .age(19)
            .build();
    }

    public static Book createTestbookA() {
        return Book.builder()
        .isbn("3445-332-5566-4433")
        .title("Negotitor")
        .author_id(1L)
        .build();
    }

    public static Book createTestbookB() {
        return Book.builder()
        .isbn("3445-332-5566-7736")
        .title("The Scars")
        .author_id(1L)
        .build();
    }

    public static Book createTestbookC() {
        return Book.builder()
        .isbn("3445-332-5566-7765")
        .title("The Life")
        .author_id(1L)
        .build();
    }
}
