package com.edigeest.journalentry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String actualMsg) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                        
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(actualMsg);
            javaMailSender.send(simpleMailMessage); 
        } catch (Exception e) {
            log.error("Exception while Sendmail", e);
        }
    }
}
