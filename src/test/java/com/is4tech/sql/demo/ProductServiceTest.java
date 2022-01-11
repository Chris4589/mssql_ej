package com.is4tech.sql.demo;

import com.is4tech.sql.demo.models.Products;
import com.is4tech.sql.demo.services.dto.IProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {
  @MockBean
  private IProductDTO productDTO;

  @Test
  void createProduct() {
    var product = new Products();
    product.setPrice(55.0);
    product.setProduct_id(1L);
    product.setDescription("description test");

    Mockito.when(this.productDTO.save(product, 1L)).thenReturn(product);

    var product_created = this.productDTO.save(product, 1L);

    Assertions.assertEquals(product_created.getProduct_id(), product.getProduct_id());
    Assertions.assertEquals(product_created.getDescription(), product.getDescription());
    Assertions.assertEquals(product_created.getPrice(), product.getPrice());
  }

  @Test
  void createProductFailed() {
    var product = new Products();
    product.setPrice(55.0);
    product.setProduct_id(1L);
    product.setDescription("description test");

    Mockito.when(this.productDTO.save(product, 1L)).thenReturn(null);

    var product_created = this.productDTO.save(product, 1L);

    Assertions.assertEquals(product_created, null);
  }

  @Test
  void getProducts() {
    var product = new Products();
    product.setPrice(55.0);
    product.setProduct_id(1L);
    product.setDescription("description test");
    var products = new ArrayList<Products>();
    products.add(product);

    Mockito.when(this.productDTO.getProducts()).thenReturn(products);

    var products_selected = this.productDTO.getProducts();

    Assertions.assertEquals(1, products_selected.size());
  }

  @Test
  void getProduct() {
    var product = new Products();
    product.setPrice(55.0);
    product.setProduct_id(1L);
    product.setDescription("description test");

    Mockito.when(this.productDTO.getById(1L)).thenReturn(Optional.of(product));

    var products_selected = this.productDTO.getById(1L);

    Assertions.assertEquals(product.getPrice(), products_selected.get().getPrice());
    Assertions.assertEquals(product.getProduct_id(), products_selected.get().getProduct_id());
    Assertions.assertEquals(product.getDescription(), products_selected.get().getDescription());
  }

  @Test
  void updateProduct() {
    var product = new Products();
    product.setPrice(55.0);
    product.setProduct_id(1L);
    product.setDescription("description test");

    Mockito.when(this.productDTO.getUpdateId(1L, product)).thenReturn(product);

    var products_selected = this.productDTO.getUpdateId(1L, product);

    Assertions.assertEquals(product.getPrice(), products_selected.getPrice());
    Assertions.assertEquals(product.getProduct_id(), products_selected.getProduct_id());
    Assertions.assertEquals(product.getDescription(), products_selected.getDescription());
  }

  @Test
  void updateProductFailed() {
    var product = new Products();
    product.setPrice(55.0);
    product.setProduct_id(1L);
    product.setDescription("description test");

    Mockito.when(this.productDTO.getUpdateId(1L, product)).thenReturn(null);

    var products_selected = this.productDTO.getUpdateId(1L, product);

    Assertions.assertEquals(products_selected, null);
  }
}
