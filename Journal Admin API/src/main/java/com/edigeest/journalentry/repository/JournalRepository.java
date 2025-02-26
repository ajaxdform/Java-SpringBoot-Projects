package com.edigeest.journalentry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigeest.journalentry.entity.JournalEntity;

public interface JournalRepository extends MongoRepository<JournalEntity, String> {
    
}
