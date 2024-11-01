package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Category;
import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.Supplier;
import com.example.proiect_forest.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
@SpringBootApplication
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Transactional
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }
    @Transactional
    @Override
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setProductId(id);
            return productRepository.save(product);
        }
        return null;
    }
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void addProduct(Product product, Category category, Supplier supplier) {

        product.setCategory(category);
        product.setSupplier(supplier);

        category.getProducts().add(product);
        supplier.getProducts().add(product);

        productRepository.save(product);
    }
}
