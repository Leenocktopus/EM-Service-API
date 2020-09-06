package com.leandoer.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandoer.security.data.AuthenticationRequest;
import com.leandoer.security.data.AuthenticationResponse;
import com.leandoer.security.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    AuthenticationManager authenticationManager;
    JwtService jwtAccessService;
    JwtService jwtRefreshService;
    ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtService jwtAccessService,
                                   JwtService jwtRefreshService,
                                   ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtAccessService = jwtAccessService;
        this.jwtRefreshService = jwtRefreshService;
        this.objectMapper = objectMapper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = jwtAccessService.generateToken(authResult.getName(), authResult.getAuthorities());
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.getWriter().write(new ObjectMapper()
                .writeValueAsString(new AuthenticationResponse(token)));
        Cookie refreshCookie = new Cookie("shj", jwtRefreshService.generateToken(authResult.getName(),
                authResult.getAuthorities()));
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(refreshCookie);
    }
}
