package com.edigeest.journalentry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.services.UserDetailsServiceImpl;
import com.edigeest.journalentry.services.UserService;
import com.edigeest.journalentry.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping(path = "/sighup")
    public ResponseEntity<Users> signup(@RequestBody Users users) {
        userService.saveEntry(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody Users users) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));

            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(users.getUsername());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occured when createAuthenticationToken", e);
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }
}
