package com.edigeest.journalentry.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigeest.journalentry.repository.UserRepoImpl;
import com.mongodb.assertions.Assertions;

@SpringBootTest
class UserRepoImplTest {

    @Autowired
    private UserRepoImpl userRepoImpl;
    
    @Test
    void toSaveNewUSer() {
        Assertions.assertNotNull(userRepoImpl.getUserForSA());
    }
}
