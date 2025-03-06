package com.example.projectlombok.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BookTest {
    @Test
    public void testThatCreateBookwithNoArgumentCon() {
        final Book book = new Book();
        assertThat(book).isNotNull();
    }

    @Test
    public void testThatCreateBookwithAllArgumentCon() {
        final Author author = new Author();
        final Book book = new Book("Lust Seeker", 2003, author);
        assertThat(book.getTitle()).isEqualTo("Lust Seeker");
        assertThat(book.getYearPublished()).isEqualTo(2003);
        assertThat(book.getAuthor()).isEqualTo(author);
    }

    @Test
    public void testThatSetGetGivenName() {
        final Book book = new Book();
        book.setTitle("Lust Seeker");
        assertThat(book.getTitle()).isEqualTo("Lust Seeker");
    }

    @Test
    public void testThatSetGetFamilyName() {
        final Book book = new Book();
        book.setYearPublished(2003);
        assertThat(book.getYearPublished()).isEqualTo(2003);
    }
}
