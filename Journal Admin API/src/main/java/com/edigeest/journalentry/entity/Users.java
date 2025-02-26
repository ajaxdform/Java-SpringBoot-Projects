package com.edigeest.journalentry.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    
    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntity> journalEntity = new ArrayList<>();

    private List<String> roles;
}
