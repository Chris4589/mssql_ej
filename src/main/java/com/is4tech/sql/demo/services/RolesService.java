package com.is4tech.sql.demo.services;

import com.is4tech.sql.demo.models.Roles;
import com.is4tech.sql.demo.repository.IRoleRepository;
import com.is4tech.sql.demo.services.dto.IRolesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService implements IRolesDTO {
  private final IRoleRepository roleRepo;

  public RolesService(IRoleRepository roleRepo) {
    this.roleRepo = roleRepo;
  }

  @Override
  public List<Roles> findByAuthority(String authority) {
    return this.roleRepo.findByAuthority(authority);
  }

  @Override
  public Roles save(Roles role) {
    return roleRepo.save(role);
  }
}
