package com.example.projectlombok.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorTest {

    @Test
    public void testThatCreateAuthorwithNoArgumentCon() {
        final Author author = new Author();
        assertThat(author).isNotNull();
    }

    @Test
    public void testCanCreateAuthorWithAllAgrumentConstructor() {
        final Author author = new Author(
            "Ajax", 
            "Xforce", 
            23);

            assertThat(author.getGivenName()).isEqualTo("Ajax");
            assertThat(author.getFamilyName()).isEqualTo("Xforce");
            assertThat(author.getAge()).isEqualTo(23);
    }

    @Test
    public void testThatSetGetGivenName() {
        final Author author = new Author();
        author.setGivenName("Shubham");
        assertThat(author.getGivenName()).isEqualTo("Shubham");
    }

    @Test
    public void testThatSetGetFamilyName() {
        final Author author = new Author();
        author.setFamilyName("Salaskar");
        assertThat(author.getFamilyName()).isEqualTo("Salaskar");
    }
}
