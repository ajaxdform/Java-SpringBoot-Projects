package com.edigeest.journalentry.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigeest.journalentry.services.EmailService;

@SpringBootTest
public class EmailServiceTest {
    
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail() {
        emailService.sendMail(
            "ajaxdform404@gmail.com", 
            "Testing java Send Mail", 
            "Extended org.springframework.mail.MailSender interface for JavaMail, supporting MIME messages both as direct arguments and through preparation callbacks. Typically used in conjunction with the MimeMessageHelper class for convenient creation of JavaMail MimeMessages, including attachments etc.");
    }
}
