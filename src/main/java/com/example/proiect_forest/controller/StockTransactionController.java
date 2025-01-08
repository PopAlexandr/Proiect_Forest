package com.example.proiect_forest.controller;

import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.StockTransaction;
import com.example.proiect_forest.service.StockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockTransactionController {

    @Autowired
    private StockTransactionService stockTransactionService;

    @PostMapping
    public void createStockTransaction(@RequestBody StockTransaction stockTransaction, Product product) {
         stockTransactionService.saveStockTransaction(stockTransaction,product);
    }

    @GetMapping("/{id}")
    public StockTransaction getStockTransactionById(@PathVariable Long id) {
        return stockTransactionService.getStockTransactionById(id);
    }

    @GetMapping("/all")
    public List<StockTransaction> getAllStockTransactions() {
        return stockTransactionService.getAllStockTransactions();
    }

    @PutMapping("/{id}")
    public void updateStockTransaction(@PathVariable Long id, @RequestBody StockTransaction stockTransaction) {
         stockTransactionService.updateStockTransaction(id, stockTransaction);
    }

    @DeleteMapping("/{id}")
    public void deleteStockTransaction(@PathVariable Long id) {
        stockTransactionService.deleteStockTransaction(id);
    }
}
