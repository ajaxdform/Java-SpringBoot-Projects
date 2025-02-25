package com.edigeest.journalentry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edigeest.journalentry.entity.Users;
import com.edigeest.journalentry.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);

        if(users != null) {
            return User.builder()
                        .username(users.getUsername())
                        .password(users.getPassword())
                        .roles(users.getRoles().toArray(new String[0]))
                        .build();
        }

        throw new UsernameNotFoundException(username);
    }
}
