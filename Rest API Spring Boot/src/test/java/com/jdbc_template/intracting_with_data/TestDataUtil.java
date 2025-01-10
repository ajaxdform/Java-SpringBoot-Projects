package com.jdbc_template.intracting_with_data;

import com.jdbc_template.intracting_with_data.domain.dto.AuthorDto;
import com.jdbc_template.intracting_with_data.domain.dto.BookDto;
import com.jdbc_template.intracting_with_data.domain.entities.AuthorEntity;
import com.jdbc_template.intracting_with_data.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil() {

    }

    public static AuthorEntity createTesAuthorA() {
        return AuthorEntity.builder()
            .id(1L)
            .name("Shubham Salaskar")
            .age(80)
            .build();
    }

    public static AuthorDto testAuthorDtoA() {
        return AuthorDto.builder()
            .id(1L)
            .name("Shubham Salaskar")
            .age(80)
            .build();
    }

    public static AuthorEntity createTesAuthorB() {
        return AuthorEntity.builder()
            .id(2L)
            .name("Vaibhav Shinde")
            .age(44)
            .build();
    }

    public static AuthorEntity createTesAuthorC() {
        return AuthorEntity.builder()
            .id(3L)
            .name("Harshita XX")
            .age(24)
            .build();
    }

    public static BookEntity createTestbookEntityA(final AuthorEntity author) {
        return BookEntity.builder()
        .isbn("3445-332-5566-4433")
        .title("Negotitor")
        .authorEntity(author)
        .build();
    }
    public static BookDto createTestbookDtoA(final AuthorDto author) {
        return BookDto.builder()
        .isbn("3445-332-5566-4433")
        .title("Negotitor")
        .author(author)
        .build();
    }

    public static BookDto createTestBookA(final AuthorDto author) {
        return BookDto.builder()
        .isbn("3445-332-5566-4433")
        .title("Negotitor")
        .author(author)
        .build();
    }

    public static BookEntity createTestbookB(final AuthorEntity author) {
        return BookEntity.builder()
        .isbn("3445-332-5566-7736")
        .title("The Scars")
        .authorEntity(author)
        .build();
    }

    public static BookEntity createTestbookC(final AuthorEntity author) {
        return BookEntity.builder()
        .isbn("3445-332-5566-7765")
        .title("The Life")
        .authorEntity(author)
        .build();
    }
}
