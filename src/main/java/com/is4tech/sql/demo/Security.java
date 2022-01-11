package com.is4tech.sql.demo;

import com.is4tech.sql.demo.auth.JWTAuthenticateFilter;
import com.is4tech.sql.demo.auth.JWTAuthorizationFilter;
import com.is4tech.sql.demo.auth.JWTValidate;
import com.is4tech.sql.demo.services.JpaUserDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled =true)
public class Security extends WebSecurityConfigurerAdapter {

  private final JpaUserDetail usrDetails;
  private final JWTValidate jwt;

  public Security(JpaUserDetail usrDetails, JWTValidate jwt) {
    this.usrDetails = usrDetails;
    this.jwt = jwt;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(usrDetails);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/v2/api-docs").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticateFilter(authenticationManager(), this.jwt))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), this.jwt))
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

  }


}
