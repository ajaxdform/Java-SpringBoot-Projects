package com.edigeest.journalentry.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.edigeest.journalentry.enums.Sentiment;
import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "journal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntity {

    @Id
    private String id;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

    private Sentiment sentiment;
}
