package com.is4tech.sql.demo.services;

import com.is4tech.sql.demo.models.Products;
import com.is4tech.sql.demo.repository.IProductRepository;
import com.is4tech.sql.demo.repository.IUserRepository;
import com.is4tech.sql.demo.services.dto.IProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductDTO {
  private final IProductRepository productRepo;
  private final IUserRepository userRepo;

  public ProductService(IProductRepository productRepo, IUserRepository userRepo) {
    this.productRepo = productRepo;
    this.userRepo = userRepo;
  }

  @Override
  public Products save(Products product, Long usr_id) {
    var user = this.userRepo.findById(usr_id);

    if (user.isEmpty()) {
      return null;
    }
    product.setUser(user.get());
    return this.productRepo.save(product);
  }

  @Override
  public List<Products> getProducts() {
    var products = this.productRepo.findAll();
    return products;
  }

  @Override
  public Optional<Products> getById(Long id) {
    var product = this.productRepo.findById(id);
    return product;
  }

  @Override
  public void deleteById(Long id) {
    this.productRepo.deleteById(id);
  }

  @Override
  public Products getUpdateId(Long id, Products product) {
    var select_product = this.productRepo.findById(id);
    if (select_product.isEmpty()) {
      return null;
    }
    select_product.get().setDescription(product.getDescription());
    select_product.get().setPrice(product.getPrice());
    return this.productRepo.save(select_product.get());
  }
}
