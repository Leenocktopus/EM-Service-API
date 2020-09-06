package com.leandoer.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.function.Function;

@Service("JwtRefreshService")
@PropertySource("classpath:security.properties")
public class JwtRefreshService extends JwtService {

    @Value("${jwt.refresh.secret}")
    String jwtRefreshSecret;

    @Value("${jwt.refresh.expiration.seconds}")
    int refreshExpirationTimeInSec;

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) throws ExpiredJwtException {
        return claimsTFunction.apply((Claims) Jwts.parser().setSigningKey(jwtRefreshSecret).parse(token).getBody());
    }

    @Override
    public String generateToken(String principal, Collection<? extends GrantedAuthority> authorities) {
        return super.generateStub(principal, authorities)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plus(refreshExpirationTimeInSec, ChronoUnit.SECONDS)))
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

}
