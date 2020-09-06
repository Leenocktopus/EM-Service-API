package com.leandoer.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:security.properties")
public abstract class JwtService {

    public abstract <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) throws ExpiredJwtException;

    public abstract String generateToken(String principal, Collection<? extends GrantedAuthority> authorities);

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        return extractClaim(token, claims -> (List<Map<String, String>>) claims.get("authorities"))
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                .collect(Collectors.toList());
    }

    public JwtBuilder generateStub(String principal, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .setSubject(principal)
                .claim("authorities", authorities);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token) throws ExpiredJwtException {
        return new UsernamePasswordAuthenticationToken(getUsername(token), null, getAuthorities(token));
    }


}
