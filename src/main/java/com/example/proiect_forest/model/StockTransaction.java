package com.example.proiect_forest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "stocktransaction", schema = "manga_shop")
public class StockTransaction {
    public StockTransaction() {

    }

    public Long getTransactionId() {
        return transactionId;
    }

    public StockTransaction(Long transactionId,  LocalDateTime transactionDate, int quantity, String transactionType) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.transactionType = transactionType;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = true)
    private String productTitle;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Column(nullable = true)
    private LocalDateTime transactionDate;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = true)
    private String transactionType;

    @Override
    public String toString() {
        return "StockTransaction{" +
                "transactionId=" + transactionId +
                ", product=" + product +
                ", transactionDate=" + transactionDate +
                ", quantity=" + quantity +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
