package com.edigeest.journalentry.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(Users users) {
        try {
            Users existing = userRepository.findByUsername(users.getUsername());

            if (existing != null) {
                log.info("Before Save - Password: " + existing.getPassword());
            }
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            users.setRoles(Arrays.asList("USER"));
            userRepository.save(users);

            log.info("After Save - Password: " + users.getPassword());
        } catch (Exception e) {
            log.error("hhhahahhahhah", e);
        }
    }

    public void saveNewEntry(Users users) {
        userRepository.save(users);
    }

    public List<Users> getAllEntities() {
        return userRepository.findAll();
    }

    public Optional<Users> getEntityById(String id) {
        return userRepository.findById(id);
    }

    public void deleteEntity(String id) {
        userRepository.deleteById(id);
    }

    public Users findBYUser(String userName) {
        return userRepository.findByUsername(userName);
    }

    public void saveAdmin(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(users);
    }
}
