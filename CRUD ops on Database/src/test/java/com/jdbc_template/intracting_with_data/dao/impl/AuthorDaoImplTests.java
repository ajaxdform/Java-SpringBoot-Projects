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
import com.jdbc_template.intracting_with_data.domain.Author;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    public JdbcTemplate jdbcTemplate;

    @InjectMocks
    public AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTesAuthorA();

        underTest.create(author);
    
        verify(jdbcTemplate).update(
            eq("Insert into authors (id, name, age) Values(?, ?, ?)"),
            eq(1L), eq("Shubham Salaskar"), eq(22)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
            eq("Select id, name, age From authors Where id = ? Limit 1"), 
            ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
            eq(1L)
        );
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql() {
        underTest.find();

        verify(jdbcTemplate).query(
            eq("Select id, name, age from authors"), 
            ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }


    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Author author = TestDataUtil.createTesAuthorA();
        underTest.update(author.getId(), author);

        verify(jdbcTemplate).update(
            "Update authors Set  id = ?, name = ?, age = ? Where id = ?",
            1L, "Shubham Salaskar", 22, 1L
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete(1L);

        verify(jdbcTemplate).update(
            "Delete From authors Where id = ?",
            1L
        );
    }
}
 