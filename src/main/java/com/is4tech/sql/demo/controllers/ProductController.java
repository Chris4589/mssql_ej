package com.is4tech.sql.demo.controllers;

import com.is4tech.sql.demo.models.Products;
import com.is4tech.sql.demo.services.dto.IProductDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
  private final IProductDTO product;

  public ProductController(IProductDTO product) {
    this.product = product;
  }

  @PostMapping("/")
  private Products save(@RequestBody Products product, @RequestParam Long user_id) {
    return this.product.save(product, user_id);
  }

  @GetMapping("/")
  private List<Products> getProducts() {
    return this.product.getProducts();
  }

  @GetMapping("/{id}")
  private Optional<Products> getProductById(@PathVariable Long id) {
    return this.product.getById(id);
  }
  @PutMapping("/{id}")
  private Products UpdateProductByID(@PathVariable Long id, @RequestBody Products product) {
    return this.product.getUpdateId(id, product);
  }
  @DeleteMapping("/{id}")
  private void deleteProductById(@PathVariable Long id) {
    this.product.deleteById(id);
  }
}
