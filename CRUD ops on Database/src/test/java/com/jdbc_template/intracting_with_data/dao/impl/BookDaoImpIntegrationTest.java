package com.jdbc_template.intracting_with_data.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.jdbc_template.intracting_with_data.TestDataUtil;
import com.jdbc_template.intracting_with_data.dao.AuthorDao;
import com.jdbc_template.intracting_with_data.domain.Author;
import com.jdbc_template.intracting_with_data.domain.Book;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImpIntegrationTest {
    
    private AuthorDao authorDao;
    private BookDaoImpl underTest;

    @Autowired
    public BookDaoImpIntegrationTest(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedandRecalled() {
        Author author = TestDataUtil.createTesAuthorA();
        authorDao.create(author);
        Book book = TestDataUtil.createTestbookA();
        book.setAuthor_id(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result.isPresent());
        assertThat(result.get()).isEqualTo(book);
    }

    @Test 
    public void testThatMultiprlCanBeCraetedAndRecalled() {
        Author author = TestDataUtil.createTesAuthorA();
        authorDao.create(author);

        Book bookA = TestDataUtil.createTestbookA();
        bookA.setAuthor_id(author.getId());
        underTest.create(bookA);

        Book bookB = TestDataUtil.createTestbookB();
        bookB.setAuthor_id(author.getId());
        underTest.create(bookB);

        Book bookC = TestDataUtil.createTestbookC();
        bookC.setAuthor_id(author.getId());
        underTest.create(bookC);

        List<Book> result = underTest.find();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);
    }


    @Test 
    public void testThatUpdateGeneratesCorrectSQl() {
        Author authorA = TestDataUtil.createTesAuthorA();
        authorDao.create(authorA);

        Book bookA = TestDataUtil.createTestbookA();
        bookA.setAuthor_id(authorA.getId());
        underTest.create(bookA);

        bookA.setTitle("The Scammer");
        underTest.update(bookA.getIsbn(), bookA);

        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }


    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        Author authorA = TestDataUtil.createTesAuthorA();
        authorDao.create(authorA);

        Book bookA = TestDataUtil.createTestbookA();
        bookA.setAuthor_id(authorA.getId());
        underTest.create(bookA);

        underTest.delete(bookA.getIsbn());

        Optional<Book> res = underTest.findOne(bookA.getIsbn());
        assertThat(res).isEmpty();
    }
}
