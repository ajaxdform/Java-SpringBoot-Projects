package com.edigeest.journalentry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigeest.journalentry.cache.AppCache;
import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;
    
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<Users> all = userService.getAllEntities();

        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody Users user) {
        userService.saveAdmin(user);
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache() {
        appCache.init();
    }
}
