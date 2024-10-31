package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.StockTransaction;

import java.util.List;

public interface StockTransactionService {
    void saveStockTransaction(StockTransaction stockTransaction, Product product);
    StockTransaction getStockTransactionById(Long id);
    List<StockTransaction> getAllStockTransactions();
    void updateStockTransaction(Long id, StockTransaction stockTransaction);
    void deleteStockTransaction(Long id);
}
