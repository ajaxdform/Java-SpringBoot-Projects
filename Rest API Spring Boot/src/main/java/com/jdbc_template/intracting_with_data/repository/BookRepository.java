package com.jdbc_template.intracting_with_data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jdbc_template.intracting_with_data.domain.entities.BookEntity;


@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
    
}
