package com.jdbc_template.intracting_with_data.services;

import java.util.List;
import java.util.Optional;

import com.jdbc_template.intracting_with_data.domain.entities.AuthorEntity;

public interface AuthorServices {
    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void deleteAuthor(Long id);
}
