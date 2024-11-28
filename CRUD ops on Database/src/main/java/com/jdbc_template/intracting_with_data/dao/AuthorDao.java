package com.jdbc_template.intracting_with_data.dao;

import java.util.List;
import java.util.Optional;

import com.jdbc_template.intracting_with_data.domain.Author;

public interface AuthorDao {
    void create(Author author);

    List<Author> find();

    Optional<Author> findOne(long l);

    void update(long id, Author author);

    void delete(long id);
}
