package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Category;
import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.Supplier;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ProductService {

    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);



    @Transactional
    void addProduct(Product product);
}
