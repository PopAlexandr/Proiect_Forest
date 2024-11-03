package com.example.proiect_forest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category", schema = "manga_shop")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Category() {

    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> products=new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }
}
