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

@Service("JwtAccessService")
@PropertySource("classpath:security.properties")
public class JwtAccessService extends JwtService {

    @Value("${jwt.access.secret}")
    String jwtAccessSecret;

    @Value("${jwt.access.expiration.seconds}")
    int accessExpirationTimeInSec;

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) throws ExpiredJwtException {
        return claimsTFunction.apply(Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token).getBody());
    }

    @Override
    public String generateToken(String principal, Collection<? extends GrantedAuthority> authorities) {
        return super.generateStub(principal, authorities)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plus(accessExpirationTimeInSec, ChronoUnit.SECONDS)))
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .compact();
    }
}
