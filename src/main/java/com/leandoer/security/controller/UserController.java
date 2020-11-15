package com.leandoer.security.controller;

import com.leandoer.exception.EntityConflictException;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.security.data.Admin;
import com.leandoer.security.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    @PatchMapping("/users/{username}/profile")
    public Admin changeProfile(@PathVariable("username") String username, @RequestBody Admin admin) {
        Admin user = userRepository
                .getAdminByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' has not been found."));
        user.setFirstName(admin.getFirstName());
        user.setLastName(admin.getLastName());
        return userRepository.save(user);
    }


    @PatchMapping("/users/{username}/email")
    public Admin changeEmail(@PathVariable("username") String username, @RequestBody Admin admin) {
        Admin user = userRepository
                .getAdminByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' has not been found."));
        user.setEmail(admin.getEmail());
        return userRepository.save(user);
    }

    @PatchMapping("/users/{username}/password")
    public Admin changePassword(@PathVariable("username") String username, @RequestBody Admin admin, @RequestParam("old") String oldPassword) {
        Admin user = userRepository
                .getAdminByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' has not been found."));
        if (!passwordEncoder.matches(admin.getPassword(), user.getPassword())) {
            if (passwordEncoder.matches(oldPassword, user.getPassword())){
                user.setVersion(user.getVersion() + 1);
                user.setPassword(passwordEncoder.encode(admin.getPassword()));
            } else {
                throw new RuntimeException("Password is not correct!");
            }

        } else{
            throw new RuntimeException("You entered the same password!");
        }
        return userRepository.save(user);
    }

    @PatchMapping("/users/{username}/username")
    public Admin changeUsername(@PathVariable("username") String username, @RequestBody Admin admin) {
        Admin user = userRepository
                .getAdminByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' has not been found."));
        if (!user.getUsername().equals(admin.getUsername())) {
            if (userRepository.existsByUsername(admin.getUsername())) {
                throw new RuntimeException("Can't change user, there is already a user with name '" + admin.getUsername() + "'.");
            } else {
                user.setUsername(admin.getUsername());
            }
        }
        return userRepository.save(user);
    }
}
