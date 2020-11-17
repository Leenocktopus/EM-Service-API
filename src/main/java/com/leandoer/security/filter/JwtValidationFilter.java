package com.leandoer.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandoer.controller.MainController;
import com.leandoer.exception.ErrorEntity;
import com.leandoer.security.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

	JwtService jwtService;
	ObjectMapper objectMapper;

	@Autowired
	public JwtValidationFilter(@Qualifier("jwtAccessService") JwtService jwtService,
	                           ObjectMapper objectMapper) {
		this.jwtService = jwtService;
		this.objectMapper = objectMapper;
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
	            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
	            objectMapper.writeValue(
			            httpServletResponse.getWriter(),
			            ErrorEntity.builder()
					            .errorCode(HttpStatus.FORBIDDEN.value())
					            .errorText(HttpStatus.FORBIDDEN.getReasonPhrase())
					            .meassage(ex.getMessage())
					            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
					            .build().add(
					            new Link(httpServletRequest.getRequestURI()).withSelfRel(),
					            linkTo(methodOn(MainController.class).root()).withRel("root")
			            ));
            }
        } else {
	        filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }
}
