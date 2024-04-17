package com.felipe.market.domain.repository;

import com.felipe.market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List <Product> getAll();
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getScarseProducts(int quantity);
    Optional<List<Product>> getLowPrices(int price); //es propio esta consulta.
    Optional<Product> getProduct(int productId);
    Product save(Product product);
    void delete(int productId);


}
