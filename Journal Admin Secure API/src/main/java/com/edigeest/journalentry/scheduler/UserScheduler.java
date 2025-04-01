package com.edigeest.journalentry.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edigeest.journalentry.cache.AppCache;
import com.edigeest.journalentry.entity.JournalEntity;
import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.enums.Sentiment;
import com.edigeest.journalentry.model.SentimentData;
import com.edigeest.journalentry.repository.UserRepoImpl;
import com.edigeest.journalentry.services.EmailService;

@Component
public class UserScheduler {

    @Autowired
    private UserRepoImpl UserRepoImpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache  appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    // @Scheduled(cron = "0 0 9 ? * SUN *")
    @Scheduled(cron = "0 0/1 * 1/1 * ? *")
    public void fetchUsersAndSendMail() {
        List<Users> users = UserRepoImpl.getUserForSA();

        for (Users user : users) {
            List<JournalEntity> journalEntities = user.getJournalEntity();
            List<Sentiment> filteredSentiments = journalEntities.stream()
                    .filter(
                            x -> x.getDate()
                                    .isAfter(
                                            LocalDateTime.now()
                                                    .minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for(Sentiment sentiment : filteredSentiments) {
                if(sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentment = null;

            int maxCount = 0;

            for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentment = entry.getKey();
                }
            }

            if(mostFrequentSentment != null) {
                SentimentData  sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("sentiment for last 7 days " + mostFrequentSentment).build();
                try {

                    // Apache Kafka is not connected due to cloud payment issue, till now emailService is there.
                    kafkaTemplate.send("Weekly-sentiments", sentimentData.getEmail(), sentimentData);
                } catch (Exception e) {
                    emailService.sendMail(sentimentData.getEmail(), "sentimenst of previous week", sentimentData.getSentiment());
                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
