package com.leandoer.security.controller;

import com.leandoer.security.data.*;
import com.leandoer.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;

@RestController
public class AuthController {

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    JwtService jwtAccess;
    JwtService jwtRefresh;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          @Qualifier("jwtAccessService") JwtService jwtAccess,
                          @Qualifier("jwtRefreshService") JwtService jwtRefresh) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtAccess = jwtAccess;
        this.jwtRefresh = jwtRefresh;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest, HttpServletResponse response) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String refreshToken = jwtRefresh.generateToken((JwtAdmin) auth.getPrincipal(), auth.getAuthorities());
        Cookie refreshCookie = new Cookie("shj", refreshToken);
        refreshCookie.setMaxAge(Integer.MAX_VALUE);
        refreshCookie.setHttpOnly(true);
        response.addCookie(refreshCookie);

        String accessToken = jwtAccess.generateToken((JwtAdmin) auth.getPrincipal(), auth.getAuthorities());
        return new AuthenticationResponse(accessToken);
    }

    @GetMapping("/refresh")
    public AuthenticationResponse refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            throw new RuntimeException("No cookie with refresh token!");
        } else {
            Cookie refreshCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("shj"))
                    .findFirst().orElseThrow(() -> new RuntimeException("No cookie with refresh token!"));
            String refreshToken = refreshCookie.getValue();
            Admin selected = userRepository.getAdminByUsername(jwtRefresh.getUsername(refreshToken))
                    .orElseThrow(() -> new RuntimeException("No user found for this jwt"));
            if (Integer.parseInt(jwtRefresh.getVersion(refreshToken)) != selected.getVersion()) {
                throw new RuntimeException("jwt versions don't match");
            }
            JwtAdmin jwtAdmin = new JwtAdmin();
            jwtAdmin.setUsername(jwtRefresh.getUsername(refreshToken));
            jwtAdmin.setVersion(Integer.parseInt(jwtRefresh.getVersion(refreshToken)));
            Collection<? extends GrantedAuthority> authorities = jwtRefresh.getAuthorities(refreshToken);
            refreshCookie.setValue(jwtRefresh.generateToken(jwtAdmin, authorities));
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(Integer.MAX_VALUE);
            httpServletResponse.addCookie(refreshCookie);
            return new AuthenticationResponse(jwtAccess.generateToken(jwtAdmin, authorities));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            throw new RuntimeException("No cookie with refresh token!");
        } else {
            Cookie refreshCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("shj"))
                    .findFirst().orElseThrow(() -> new RuntimeException("No cookie with refresh token!"));
            String refreshToken = refreshCookie.getValue();
            Admin selected = userRepository.getAdminByUsername(jwtRefresh.getUsername(refreshToken))
                    .orElseThrow(() -> new RuntimeException("No user found for this jwt"));
            selected.setVersion(selected.getVersion() + 1);
            userRepository.save(selected);
        }

        return ResponseEntity.ok().build();
    }


}
