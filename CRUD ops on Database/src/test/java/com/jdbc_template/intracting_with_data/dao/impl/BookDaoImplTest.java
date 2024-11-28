package com.jdbc_template.intracting_with_data.dao.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jdbc_template.intracting_with_data.TestDataUtil;
import com.jdbc_template.intracting_with_data.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookgeneratescorrectSql() {
        Book book = TestDataUtil.createTestbookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
            eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
            eq("3445-332-5566-4433"), eq("Negotitor"), eq(1L)
        );
    }


    @Test
    public void testThatFindOneBookGeneratesCorrectSql() {
        underTest.findOne("3445-332-5566-4433");

        verify(jdbcTemplate).query(
            eq("Select isbn, title, author_id From books Where isbn = ? Limit 1"), 
            ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
            eq("3445-332-5566-4433")
        );
    }

    @Test
    public void testThatFindBookGeneratesCorrectSql() {
        underTest.find();

        verify(jdbcTemplate).query(
            eq("Select isbn, title, author_id From books"),
            ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test 
    public void testThatUpdateGenerateCorrectSql() {
        Book book = TestDataUtil.createTestbookA();
        underTest.update(book.getIsbn() ,book);

        verify(jdbcTemplate).update(
            "Update books Set isbn = ?, title =?, author_id = ? Where isbn = ?",
            "3445-332-5566-4433", "Negotitor", 1L, "3445-332-5566-4433"
        );
    }

    @Test
    public void tetsThatDeleteGeneratesCorrectSql() {
        underTest.delete("3445-332-5566-4433");

        verify(jdbcTemplate).update(
            "Delete from books Where isbn = ?", 
            "3445-332-5566-4433"
        );
    }
}
