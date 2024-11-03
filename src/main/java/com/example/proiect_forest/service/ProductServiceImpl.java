package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Category;
import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.Supplier;
import com.example.proiect_forest.repository.CategoryRepository;
import com.example.proiect_forest.repository.ProductRepository;
import com.example.proiect_forest.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@SpringBootApplication
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
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

    @Override
    @Transactional
    public void addProduct(Product product) {

        // Handle Category
        Category category = product.getCategory();
        if (category != null) {
            Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
            Category finalCategory = category;
            category = existingCategory.orElseGet(() -> categoryRepository.save(finalCategory));
            product.setCategory(category);
        }

        // Handle Supplier
        Supplier supplier = product.getSupplier();
        if (supplier != null) {
            if (supplier.getSupplierId() != null) {
                Supplier finalSupplier = supplier;
                supplier = supplierRepository.findById(supplier.getSupplierId())
                        .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + finalSupplier.getSupplierId()));
            } else if (supplier.getName() != null) {
                Optional<Supplier> existingSupplier = supplierRepository.findByName(supplier.getName());
                Supplier finalSupplier1 = supplier;
                supplier = existingSupplier.orElseGet(() -> supplierRepository.save(finalSupplier1));
            }
            product.setSupplier(supplier);
        }

        productRepository.save(product);
    }
}
