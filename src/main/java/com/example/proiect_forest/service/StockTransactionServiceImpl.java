package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.StockTransaction;
import com.example.proiect_forest.repository.StockTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class StockTransactionServiceImpl implements StockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;
    @Transactional
    @Override
    public void saveStockTransaction(StockTransaction stockTransaction, Product product) {
        stockTransaction.setProduct(product);
        stockTransaction.setProductTitle(product.getTitle());
        product.getStockTransaction().add(stockTransaction);


        stockTransactionRepository.save(stockTransaction);
    }
    @Transactional
    @Override
    public StockTransaction getStockTransactionById(Long id) {
        return stockTransactionRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public List<StockTransaction> getAllStockTransactions() {
        return stockTransactionRepository.findAll();
    }
    @Transactional
    @Override
    public void updateStockTransaction(Long id, StockTransaction stockTransaction) {
        if (stockTransactionRepository.existsById(id)) {
            stockTransaction.setTransactionId(id);
            stockTransactionRepository.save(stockTransaction);
        }

    }

    public StockTransactionServiceImpl(StockTransactionRepository stockTransactionRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
    }
    @Transactional
    @Override
    public void deleteStockTransaction(Long id) {
        stockTransactionRepository.deleteById(id);
    }

    @Override
    public Map<String, Integer> getTransactionSummary() {
        List<StockTransaction> transactions = stockTransactionRepository.findAll();
        Map<String, Integer> summary = new HashMap<>();

        summary.put("ADD", transactions.stream()
                .filter(t -> "ADD".equals(t.getTransactionType()))
                .mapToInt(StockTransaction::getQuantity)
                .sum());

        summary.put("REMOVE", transactions.stream()
                .filter(t -> "REMOVE".equals(t.getTransactionType()))
                .mapToInt(StockTransaction::getQuantity)
                .sum());

        summary.put("DELETE", transactions.stream()
                .filter(t -> "DELETE".equals(t.getTransactionType()))
                .mapToInt(StockTransaction::getQuantity)
                .sum());

        return summary;
    }
}
