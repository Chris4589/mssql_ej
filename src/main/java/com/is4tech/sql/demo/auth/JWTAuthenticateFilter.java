package com.is4tech.sql.demo.auth;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.is4tech.sql.demo.models.User;
import com.is4tech.sql.demo.services.JpaUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authManager;
  private final JWTValidate jwt;
  private Logger logger = LoggerFactory.getLogger(JpaUserDetail.class);

  public JWTAuthenticateFilter(AuthenticationManager authManager, JWTValidate jwt) {
    this.authManager = authManager;
    this.jwt = jwt;
  }

  @Override
  protected String obtainUsername(HttpServletRequest request) {
    return request.getParameter("email");
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    var token = this.jwt.JWT_Sign(authResult);
    this.logger.info(authResult.toString());
    Map<String, Object> body = new HashMap<String, Object>();
    body.put("err", false);
    body.put("token", token);
    body.put("roles", authResult.getAuthorities());
    body.put("msg", authResult.getName());

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setStatus(200);
    response.setContentType("application/json");
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    Map<String, Object> body = new HashMap<String, Object>();

    body.put("msg", "Ocurrió un error: " + failed.getMessage());
    body.put("error", true);
    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setStatus(401);
    response.setContentType("application/json");
  }

  //sobreescribir method que intenta autentificar
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

    String username = obtainUsername(request);
    username = (username != null) ? username : "";
    username = username.trim();
    String password = obtainPassword(request);
    password = (password != null) ? password : "";

    //para recibir un JSON
    if (username == "" && password == "") {
      try {
        User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        username = user.getEmail();
        password = user.getPassword();
      } catch (JsonParseException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
    return authManager.authenticate(authToken);
  }


}
