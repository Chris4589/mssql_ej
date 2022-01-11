package com.is4tech.sql.demo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  private final JWTValidate jwt;
  private Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);


  public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTValidate jwt) {
    super(authenticationManager);
    this.jwt = jwt;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader("token");

    if(!isReqAuth(header)) {
      logger.info("NO HAY HEADERS!!!");
      chain.doFilter(request, response);
      return;
    }
    boolean validToken = true;
    Jws<Claims> token = null;

    try {
      token = jwt.JWT_validate(header.replace("Bearer ", ""));
      validToken = true;
    } catch (Exception e) {
      validToken = false;
      logger.info("NO Se puedo validar token: "+ e.getMessage());
      
      Map<String, Object> json = new HashMap<String, Object>();
      json.put("err", true);
      json.put("msg", "No se pudo verificar el token");

      response.getWriter().write(new ObjectMapper().writeValueAsString(json));
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.setStatus(500);
      return;
    }
    UsernamePasswordAuthenticationToken authToken = null;
    if (validToken) {

      String username = token.getBody().getSubject();
      Object roles = token.getBody().get("roles"); //JWTAuthFilter

      Collection<? extends GrantedAuthority> authorities = Arrays
              .asList(new ObjectMapper()
                      .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                      .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class)
              );

      System.out.println("valido " + authorities);
      authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

    }
    SecurityContextHolder.getContext().setAuthentication(authToken);
    chain.doFilter(request, response);
  }
  protected boolean isReqAuth(String header) {

    if (header == null || !header.startsWith("Bearer ")) {
      return false;
    }
    return true;
  }
}
