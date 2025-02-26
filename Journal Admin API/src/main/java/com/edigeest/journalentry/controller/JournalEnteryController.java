package com.edigeest.journalentry.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigeest.journalentry.entity.JournalEntity;
import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.services.JournalService;
import com.edigeest.journalentry.services.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEnteryController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntitiesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userService.findBYUser(username);

        List<JournalEntity> all = user.getJournalEntity();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userService.findBYUser(username);

        List<JournalEntity> collect = user.getJournalEntity().stream().filter(x -> x.getId().equals(id))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntity> foundOne = journalService.getEntityById(id);
            if (foundOne.isPresent()) {
                return new ResponseEntity<>(foundOne, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/createEntity")
    public ResponseEntity<JournalEntity> createEntity(@RequestBody JournalEntity myEntity) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalService.saveEntry(myEntity, username);
            return new ResponseEntity<>(myEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/updateEntity/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable String id, @RequestBody JournalEntity newEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userService.findBYUser(username);

        List<JournalEntity> collect = user.getJournalEntity().stream().filter(x -> x.getId().equals(id))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntity> foundONe = journalService.getEntityById(id);
            if (foundONe.isPresent()) {
                JournalEntity old = foundONe.get();
                old.setTitle(newEntity.getTitle() != null && !newEntity.getTitle().equals("") ? newEntity.getTitle()
                    : old.getTitle());
                old.setContent(newEntity.getContent() != null && !newEntity.getContent().equals("") ? newEntity.getContent()
                    : old.getContent());
            
                journalService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(path = "/deleteEntity/{id}")
    public ResponseEntity deleteEntity(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalService.deleteEntity(id, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
