package com.is4tech.sql.demo.services;

import com.is4tech.sql.demo.models.Roles;
import com.is4tech.sql.demo.models.User;
import com.is4tech.sql.demo.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaUserDetail implements UserDetailsService {//se implementa la interface default de spring se..

  public final IUserRepository userDTO;
  private Logger logger = LoggerFactory.getLogger(JpaUserDetail.class);

  public JpaUserDetail(IUserRepository userDTO) {
    this.userDTO = userDTO;
  }

  @Override
  @Transactional(readOnly = true)//se sobre escribe este metodo
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User usr = this.userDTO.findByEmail(username);

    if(usr == null) {
      logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
      throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
    }

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (Roles roles:usr.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(roles.getAuthority()));
    }

    if(authorities.isEmpty()) {
      logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
      throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
    }
    //se retorna una nueva instancia
    return new org.springframework.security.core.userdetails.User(usr.getEmail(), usr.getPassword(), true, true, true, true, authorities);
  }
}
