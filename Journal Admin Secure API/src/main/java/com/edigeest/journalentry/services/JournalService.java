package com.edigeest.journalentry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edigeest.journalentry.entity.JournalEntity;
import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.repository.JournalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntity journalEntity, String username) {
        try {
            Users user = userService.findBYUser(username);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalRepository.save(journalEntity);
            user.getJournalEntity().add(saved);
            userService.saveNewEntry(user);
        } catch (Exception e) {
            log.error("Error occured: " , e);
            throw new RuntimeException("An error while saving the entry..", e);
        }
    }

    public void saveEntry(JournalEntity journalEntity) {
        journalRepository.save(journalEntity);
    }

    public List<JournalEntity> getAllEntities() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntity> getEntityById(String id) {
        return journalRepository.findById(id);
    }

    @Transactional
    public boolean deleteEntity(String id, String username) {
        boolean removed = false;
        try {
            Users users = userService.findBYUser(username);
            removed = users.getJournalEntity().removeIf(
                    x -> x.getId().equals(id));

            if (removed) {
                userService.saveNewEntry(users);
                journalRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Error occured: ", e);
            throw new RuntimeException("an eror occured while deleting the entity.", e);
        }
        return removed;
    }
}
