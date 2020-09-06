package com.leandoer.security.service;

import com.leandoer.security.data.Admin;
import com.leandoer.security.data.JwtAdmin;
import com.leandoer.security.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = userRepository.getAdminByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Admin with username '" + username + "' has not been found.")
        );
        return JwtAdmin.fromAdmin(admin);
    }
}
