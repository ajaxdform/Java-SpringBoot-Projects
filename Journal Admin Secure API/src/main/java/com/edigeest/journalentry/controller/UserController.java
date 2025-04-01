package com.edigeest.journalentry.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigeest.journalentry.api.WeatherResponse;
import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.repository.UserRepository;
import com.edigeest.journalentry.services.UserService;
import com.edigeest.journalentry.services.WeatherService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping(path = "/getAllUsers")
    public List<Users> getAllUsers() {
        return userService.getAllEntities();
    }

    @GetMapping(path = "/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<Users> foundOne = userService.getEntityById(id);
        return new ResponseEntity<>(foundOne, HttpStatus.OK);
    }

    @PutMapping(path = "/updateUser")
    public ResponseEntity<Users> updateUser(@RequestBody Users users) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users userInDB = userService.findBYUser(username);
        userInDB.setUsername(users.getUsername());
        userInDB.setPassword(users.getPassword());
        userInDB.setSentimentAnalysis(users.isSentimentAnalysis());
        userService.saveEntry(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/deleteUser")
    public ResponseEntity<Users> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getUserwithQuate")
    public ResponseEntity<?> getUserQuate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greetings = "";

        if(weatherResponse != null) {
            greetings = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        } 
        return new ResponseEntity<>("Hi " + authentication.getName() + " " + greetings, HttpStatus.OK);
    }
}
