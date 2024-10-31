package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Supplier;

import java.util.List;

public interface SupplierService {
    void saveSupplier(Supplier supplier);
    Supplier getSupplierById(Long id);
    List<Supplier> getAllSuppliers();
    Supplier updateSupplier(Long id, Supplier supplier);
    void deleteSupplier(Long id);
}
