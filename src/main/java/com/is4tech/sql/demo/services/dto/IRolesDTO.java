package com.is4tech.sql.demo.services.dto;

import com.is4tech.sql.demo.models.Roles;

import java.util.List;

public interface IRolesDTO {
  public List<Roles> findByAuthority(String authority);
  public Roles save(Roles role);
}
