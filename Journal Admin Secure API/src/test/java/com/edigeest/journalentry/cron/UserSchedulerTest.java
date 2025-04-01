package com.edigeest.journalentry.cron;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigeest.journalentry.scheduler.UserScheduler;

@SpringBootTest
public class UserSchedulerTest {
    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void fetchUsersAndSendMail(){
        userScheduler.fetchUsersAndSendMail();
    }
}
