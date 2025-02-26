package com.edigeest.journalentry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.services.UserService;

@RestController
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/createUsers")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        userService.saveEntry(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }
}
