package com.is4tech.sql.demo;

import com.is4tech.sql.demo.services.JpaUserDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JpaUserDetailTest {

  @Mock
  private JpaUserDetail userDetail;

  @Test
  void jpa() {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("asd"));
    var user = new User("chris@gmail.com", "password", true, true, true, true, authorities);
    Mockito.when(this.userDetail.loadUserByUsername("chris")).thenReturn(user);
    var user_selected = this.userDetail.loadUserByUsername("chris");

    Assertions.assertEquals(user.getAuthorities(), user_selected.getAuthorities());
    Assertions.assertEquals(user.getPassword(), user_selected.getPassword());
    Assertions.assertEquals(user.getUsername(), user_selected.getUsername());
  }
}
