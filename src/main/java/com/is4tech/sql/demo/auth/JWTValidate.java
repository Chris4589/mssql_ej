package com.is4tech.sql.demo.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTValidate {
  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public String JWT_Sign(Authentication authResult) throws JsonProcessingException {

    Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

    Claims claims = Jwts.claims();
    claims.put("roles", new ObjectMapper().writeValueAsString(roles));

    var token = Jwts.builder()
            .setClaims(claims)
            .setSubject(authResult.getName())
            .signWith(key)
            .setExpiration(new Date(System.currentTimeMillis() + 3600000L))
            .compact();

    return token;
  }

  public Jws<Claims> JWT_validate(String tokenHeader) {
    var token = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(tokenHeader);

    return token;
  }
}
