package com.edigeest.journalentry.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edigeest.journalentry.entity.ConfigJournalAppEntity;
import com.edigeest.journalentry.repository.ConfigJournalApp;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalApp configJournalApp;
     
    public Map<String, String> appCache;

    @PostConstruct 
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalApp.findAll();

        for(ConfigJournalAppEntity configJournalAppEntity : all) {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
