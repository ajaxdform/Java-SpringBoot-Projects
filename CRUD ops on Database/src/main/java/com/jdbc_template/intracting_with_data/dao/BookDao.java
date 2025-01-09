package com.jdbc_template.intracting_with_data.dao;
import java.util.*;
import java.util.Optional;

import com.jdbc_template.intracting_with_data.domain.Book;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> find();

    void update(String isbn, Book book);

    void delete(String isbn);
}
