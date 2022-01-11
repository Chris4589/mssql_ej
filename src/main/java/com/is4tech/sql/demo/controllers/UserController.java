package com.is4tech.sql.demo.controllers;

import com.is4tech.sql.demo.models.Roles;
import com.is4tech.sql.demo.models.User;
import com.is4tech.sql.demo.services.dto.IRolesDTO;
import com.is4tech.sql.demo.services.dto.IUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private final IUserDTO usr;


    public UserController(IUserDTO usr) {
        this.usr = usr;
    }

    @GetMapping("/")
    public Iterable<User> getUsers() {
        return this.usr.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        var usr = this.usr.findById(id);
        return usr;
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        var usr = this.usr.save(user);
        return ResponseEntity.status(200).body(usr);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return this.usr.updateById(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        this.usr.deleteById(id);
        return ResponseEntity.status(200).body("Se borro correctamente!");
    }

}
