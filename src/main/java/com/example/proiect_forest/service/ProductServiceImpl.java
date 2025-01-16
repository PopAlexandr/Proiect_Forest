package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Category;
import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.StockTransaction;
import com.example.proiect_forest.model.Supplier;
import com.example.proiect_forest.repository.CategoryRepository;
import com.example.proiect_forest.repository.ProductRepository;
import com.example.proiect_forest.repository.StockTransactionRepository;
import com.example.proiect_forest.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@SpringBootApplication
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StockTransactionRepository stockTransactionRepository;
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
        // Fetch the existing product from the database
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        int quantityDifference = product.getStockQuantity() - existingProduct.getStockQuantity();


        // Update product fields
        existingProduct.setTitle(product.getTitle());
        existingProduct.setAuthor(product.getAuthor());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setSupplier(product.getSupplier());

        // Save the updated product first
        Product updatedProduct = productRepository.save(existingProduct);

        if (quantityDifference != 0) {
            StockTransaction newTransaction = new StockTransaction();
            newTransaction.setProduct(updatedProduct);
            newTransaction.setQuantity(quantityDifference); // Set the difference as quantity
            newTransaction.setTransactionType(quantityDifference > 0 ? "ADD" : "REMOVE"); // ADD or REMOVE
            newTransaction.setTransactionDate(LocalDateTime.now());
            newTransaction.setProductTitle(updatedProduct.getTitle()); // Save product title

            // Save the new transaction
            stockTransactionRepository.save(newTransaction);
        }

        return updatedProduct;
    }


    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        trackInventoryMovement(product, -product.getStockQuantity(), "DELETE", "Product deleted");
        // Update transactions to include the product title before nullifying the product
        for (StockTransaction transaction : product.getStockTransaction()) {
            transaction.setProductTitle(product.getTitle());
            transaction.setProduct(null); // Nullify the product reference
            stockTransactionRepository.save(transaction); // Save the updated transaction
        }

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

        Product savedProduct = productRepository.save(product);
        trackInventoryMovement(savedProduct,product.getStockQuantity(),"ADD","New product added");
    }

    @Override
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getStockQuantity() < threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getTopSellingProducts(int limit) {
        // Calculate total sales for each product
        return productRepository.findAll().stream()
                .sorted((p1, p2) -> {
                    int sales1 = p1.getStockTransaction().stream()
                            .filter(tx -> "REMOVE".equalsIgnoreCase(tx.getTransactionType()))
                            .mapToInt(StockTransaction::getQuantity)
                            .sum();

                    int sales2 = p2.getStockTransaction().stream()
                            .filter(tx -> "REMOVE".equalsIgnoreCase(tx.getTransactionType()))
                            .mapToInt(StockTransaction::getQuantity)
                            .sum();

                    return Integer.compare(sales2, sales1); // Sort by descending sales
                })
                .limit(limit) // Limit the number of top-selling products
                .collect(Collectors.toList());
    }



    @Transactional
    public void trackInventoryMovement(Product product, int quantity, String type, String description) {


        StockTransaction transaction = new StockTransaction();
        transaction.setProduct(product); // Use the retrieved product
        transaction.setProductTitle(product.getTitle());
        transaction.setQuantity(quantity);
        transaction.setTransactionType(type);
        transaction.setTransactionDate(LocalDateTime.now());


        stockTransactionRepository.save(transaction);
        // Ensure no duplicate references in the product's stock transactions list
        if (product.getStockTransaction() == null) {
            product.setStockTransactions(new ArrayList<>());
        }
        product.getStockTransaction().add(transaction);

        productRepository.save(product); // Persist the updated product
    }


}
