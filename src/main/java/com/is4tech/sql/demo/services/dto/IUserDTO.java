package com.is4tech.sql.demo.services.dto;

import com.is4tech.sql.demo.models.User;

import java.util.Optional;

public interface IUserDTO {
    public User save(User user);
    public Optional<User> findById(Long id);
    public Iterable<User> findAll();
    public void deleteById(Long id);
    public User updateById(Long id, User user);
}
