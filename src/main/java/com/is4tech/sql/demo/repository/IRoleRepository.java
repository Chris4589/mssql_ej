package com.is4tech.sql.demo.repository;

import com.is4tech.sql.demo.models.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends CrudRepository<Roles, Long> {
  public List<Roles> findByAuthority(String authority);
}
