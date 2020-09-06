package com.leandoer.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandoer.exception.JwtExceptionHandler;
import com.leandoer.security.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtValidationFilter extends OncePerRequestFilter {

    JwtService jwtService;
    ObjectMapper objectMapper;
    JwtExceptionHandler jwtExceptionHandler;

    public JwtValidationFilter(JwtService jwtService, ObjectMapper objectMapper, JwtExceptionHandler jwtExceptionHandler) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.jwtExceptionHandler = jwtExceptionHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && !token.isEmpty() && token.startsWith("Bearer ")) {
            try {
                token = token.substring(7);
                UsernamePasswordAuthenticationToken authToken = jwtService.getAuthenticationFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (JwtException ex) {
                httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                httpServletResponse.setStatus(200);
                objectMapper.writeValue(
                        httpServletResponse.getWriter(),
                        jwtExceptionHandler.handleJwtException(ex, httpServletRequest)
                );
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().matches("/refresh");
    }
}
