package com.leandoer.security.controller;

import com.leandoer.exception.EntityConflictException;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.security.data.Admin;
import com.leandoer.security.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/{username}")
    public Admin getUserById(@PathVariable("username") String username) {
        return userRepository.getAdminByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' has not been found"));
    }

    @PutMapping("/users/{username}")
    public Admin changeUser(@PathVariable("username") String username, @RequestBody Admin admin) {
        Admin user = userRepository
                .getAdminByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' has not been found."));
        user.setFirstName(admin.getFirstName());
        user.setLastName(admin.getLastName());
        user.setEmail(admin.getEmail());
        if (!user.getPassword().equals(passwordEncoder.encode(admin.getPassword()))) {
            user.setVersion(user.getVersion() + 1);
            user.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        if (!user.getUsername().equals(admin.getUsername())) {
            if (userRepository.existsByUsername(admin.getUsername())) {
                throw new EntityConflictException("Can't change user, there is already a user with name '" + admin.getUsername() + "'.");
            } else {
                user.setUsername(admin.getUsername());
            }
        }
        return userRepository.save(admin);
    }

}
