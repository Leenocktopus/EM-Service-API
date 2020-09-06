package com.leandoer.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandoer.exception.JwtExceptionHandler;
import com.leandoer.security.data.AuthenticationResponse;
import com.leandoer.security.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class JwtRefreshFilter extends OncePerRequestFilter {

    JwtService jwtAccessService;
    JwtService jwtRefreshService;
    JwtExceptionHandler jwtExceptionHandler;
    ObjectMapper objectMapper;

    public JwtRefreshFilter(JwtService jwtAccessService,
                            JwtService jwtRefreshService,
                            JwtExceptionHandler jwtExceptionHandler,
                            ObjectMapper objectMapper) {
        this.jwtAccessService = jwtAccessService;
        this.jwtRefreshService = jwtRefreshService;
        this.jwtExceptionHandler = jwtExceptionHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (httpServletRequest.getCookies() == null) {
                throw new JwtException("No refresh cookie");
            }
            Cookie refreshCookie = Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> cookie.getName().equals("shj"))
                    .findAny().orElseThrow(() -> new JwtException("No refresh cookie"));

            String refreshToken = refreshCookie.getValue();
            String username = jwtRefreshService.getUsername(refreshToken);

            Collection<? extends GrantedAuthority> authorities = jwtRefreshService.getAuthorities(refreshToken);
            AuthenticationResponse response = new AuthenticationResponse(
                    jwtAccessService.generateToken(username, authorities)
            );
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(response));

            refreshCookie = new Cookie("shj", jwtRefreshService.generateToken(username, authorities));
            refreshCookie.setHttpOnly(true);
            httpServletResponse.addCookie(refreshCookie);
        } catch (JwtException ex) {
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpServletResponse.setStatus(200);
            objectMapper.writeValue(
                    httpServletResponse.getWriter(),
                    jwtExceptionHandler.handleJwtException(ex, httpServletRequest)
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().matches("/refresh");
    }
}
