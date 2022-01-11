package com.is4tech.sql.demo.services;

import com.is4tech.sql.demo.models.Roles;
import com.is4tech.sql.demo.models.User;
import com.is4tech.sql.demo.repository.IRoleRepository;
import com.is4tech.sql.demo.repository.IUserRepository;
import com.is4tech.sql.demo.services.dto.IRolesDTO;
import com.is4tech.sql.demo.services.dto.IUserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserDTO {

  public final IUserRepository usrRepo;
  public final PasswordEncoder encoder;
  private final IRoleRepository role;

  public UserService(IUserRepository usrRepo, PasswordEncoder encoder, IRoleRepository role) {
    this.usrRepo = usrRepo;
    this.encoder = encoder;
    this.role = role;
  }

  @Override
  public User save(User user) {
    user.setPassword(this.encoder.encode(user.getPassword()));
    var usr = this.usrRepo.save(user);
    var authority = new Roles();
    authority.setAuthority("ROLE_USER");
    authority.setUser(usr);
    role.save(authority);
    return usr;
  }

  @Override
  public Optional<User> findById(Long id) {
    var user = this.usrRepo.findById(id);
    return user;
  }

  @Override
  public Iterable<User> findAll() {
    var users = this.usrRepo.findAll();
    return users;
  }

  @Override
  public void deleteById(Long id) {
    var usr = this.usrRepo.findById(id);

    if (!usr.isEmpty()) {
      this.usrRepo.deleteById(id);
    }

  }

  @Override
  public User updateById(Long id, User user) {
    var usr = this.usrRepo.findById(id);

    if (!usr.isEmpty()) {
      usr.get().setEmail_alert(user.getEmail_alert());
      usr.get().setEmail(user.getEmail());
      usr.get().setPassword(encoder.encode(user.getPassword()));
      usr.get().setCode(user.getCode());
      return usrRepo.save(usr.get());
    }
    return null;
  }
}
