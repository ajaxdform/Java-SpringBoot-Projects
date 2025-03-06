package com.edigeest.journalentry.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigeest.journalentry.entity.ConfigJournalAppEntity;

public interface ConfigJournalApp extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
    
}
