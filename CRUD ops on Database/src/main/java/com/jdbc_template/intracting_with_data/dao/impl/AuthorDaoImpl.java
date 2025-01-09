package com.jdbc_template.intracting_with_data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.jdbc_template.intracting_with_data.dao.AuthorDao;
import com.jdbc_template.intracting_with_data.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
            "Insert into authors (id, name, age) Values(?, ?, ?)",
            author.getId(), author.getName(), author.getAge()
        );
    }

    @Override
    public Optional<Author> findOne(long authorId) {
        List<Author> result = jdbcTemplate.query(
            "Select id, name, age From authors Where id = ? Limit 1", new AuthorRowMapper(), 
            authorId
        );

        return result.stream().findFirst();
    }


    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        @Nullable
        public Author mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
        
    }


    @Override
    public List<Author> find() {
        return jdbcTemplate.query(
            "Select id, name, age from authors",
            new AuthorRowMapper()
        );
    }

    @Override
    public void update(long id, Author author) {
        jdbcTemplate.update(
            "Update authors Set  id = ?, name = ?, age = ? Where id = ?",
            author.getId(), author.getName(), author.getAge(), id
        );
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(
            "Delete From authors Where id = ?",
            1L
        );
    }

}
