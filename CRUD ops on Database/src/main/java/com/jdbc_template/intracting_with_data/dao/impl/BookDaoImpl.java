package com.jdbc_template.intracting_with_data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jdbc_template.intracting_with_data.dao.BookDao;
import com.jdbc_template.intracting_with_data.domain.Book;

@Component
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Book book) {
        jdbcTemplate.update(
            "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
            book.getIsbn(), book.getTitle(), book.getAuthor_id()
        );
    }

    @Override
    public Optional<Book> findOne(String isbn) {
        List<Book> result = jdbcTemplate.query("Select isbn, title, author_id From books Where isbn = ? Limit 1", new BookRowMapper(), isbn);
        return result.stream().findFirst();
    }

    public static class BookRowMapper implements RowMapper<Book> {

        public Book mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
            .isbn(rs.getString("isbn"))
            .title(rs.getString("title"))
            .author_id(rs.getLong("author_id"))
            .build();
        }
    }

    @Override
    public List<Book> find() {
         return jdbcTemplate.query(
            "Select isbn, title, author_id From books",
            new BookRowMapper()
         );
    }

    @Override
    public void update(String isbn, Book book) {
        jdbcTemplate.update(
            "Update books Set isbn = ?, title =?, author_id = ? Where isbn = ?",
            book.getIsbn(), book.getTitle(), book.getAuthor_id(), isbn
        );
    }

    @Override
    public void delete(String isbn) {
        jdbcTemplate.update(
            "Delete from books Where isbn = ?", 
            "3445-332-5566-4433"
        );
    }
}
