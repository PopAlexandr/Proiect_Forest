package com.example.proiect_forest.controller;

import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.model.StockTransaction;
import com.example.proiect_forest.service.StockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Integer>> getTransactionSummary() {
        Map<String, Integer> summary = stockTransactionService.getTransactionSummary();
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StockTransaction>> getAllStockTransactions() {
        List<StockTransaction> transactions = stockTransactionService.getAllStockTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
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
