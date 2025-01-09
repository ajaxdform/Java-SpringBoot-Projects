package com.jdbc_template.intracting_with_data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.jdbc_template.intracting_with_data.TestDataUtil;
import com.jdbc_template.intracting_with_data.domain.entities.AuthorEntity;
import com.jdbc_template.intracting_with_data.domain.entities.BookEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationtest {
    
    private BookRepository underTest;

    public BookRepositoryIntegrationtest(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedandRecalled() {
        AuthorEntity author = TestDataUtil.createTesAuthorA();
        BookEntity book = TestDataUtil.createTestbookB(author);
        underTest.save(book);
        Optional<BookEntity> result = underTest.findById(book.getIsbn());

        assertThat(result.isPresent());
        assertThat(result.get()).isEqualTo(book);
    }

    @Test 
    public void testThatMultiprlCanBeCraetedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTesAuthorA();

        BookEntity bookA = TestDataUtil.createTestbookB(author);
        underTest.save(bookA);

        BookEntity bookB = TestDataUtil.createTestbookB(author);
        underTest.save(bookB);

        BookEntity bookC = TestDataUtil.createTestbookC(author);
        underTest.save(bookC);

        Iterable<BookEntity> result = underTest.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);
    }


    @Test 
    public void testThatUpdateGeneratesCorrectSQl() {
        AuthorEntity authorA = TestDataUtil.createTesAuthorA();

        BookEntity bookA = TestDataUtil.createTestbookB(authorA);
        underTest.save(bookA);

        bookA.setTitle("The Scammer");
        underTest.save(bookA);

        Optional<BookEntity> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }


    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        AuthorEntity authorA = TestDataUtil.createTesAuthorA();

        BookEntity bookA = TestDataUtil.createTestbookB(authorA);
        underTest.save(bookA);

        underTest.deleteById(bookA.getIsbn());

        Optional<BookEntity> res = underTest.findById(bookA.getIsbn());
        assertThat(res).isEmpty();
    }
}
