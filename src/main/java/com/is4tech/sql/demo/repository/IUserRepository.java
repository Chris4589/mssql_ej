package com.is4tech.sql.demo.repository;

import com.is4tech.sql.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  public User findByEmail(String email);
}
