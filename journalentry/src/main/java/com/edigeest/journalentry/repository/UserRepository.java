package com.edigeest.journalentry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigeest.journalentry.entity.Users;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByUsername(String userName);

    void deleteByUsername(String name);
}
