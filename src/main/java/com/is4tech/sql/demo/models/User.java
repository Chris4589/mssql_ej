package com.is4tech.sql.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "suser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(length = 30, unique = true, name = "code")
    private String code;

    @Column(length = 60, name = "email")
    private String email;

    @Column(length = 60, name = "password")
    private String password;

    @Column(length = 60, name = "email_alert")
    private String email_alert;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Roles> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Products> products;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Channels> channels;

    public User(Long user_id, String code, String email, String email_alert, List<Roles> roles, List<Products> products, List<Channels> channels) {
        this.user_id = user_id;
        this.code = code;
        this.email = email;
        this.email_alert = email_alert;
        this.roles = roles;
        this.products = products;
        this.channels = channels;
    }

    public User() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_alert() {
        return email_alert;
    }

    public void setEmail_alert(String email_alert) {
        this.email_alert = email_alert;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public List<Channels> getChannels() {
        return channels;
    }

    public void setChannels(List<Channels> channels) {
        this.channels = channels;
    }
}
