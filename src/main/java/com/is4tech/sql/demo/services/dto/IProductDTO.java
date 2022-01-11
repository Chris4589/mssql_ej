package com.is4tech.sql.demo.services.dto;

import com.is4tech.sql.demo.models.Products;
import com.is4tech.sql.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface IProductDTO {
  public Products save(Products product, Long usr_id);
  public List<Products> getProducts();
  public Optional<Products> getById(Long id);
  public void deleteById(Long id);
  public Products getUpdateId(Long id, Products product);
}
