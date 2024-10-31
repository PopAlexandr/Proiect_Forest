package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Supplier;
import com.example.proiect_forest.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Transactional
    @Override
    public void saveSupplier(Supplier supplier) {
         supplierRepository.save(supplier);
    }
    @Transactional
    @Override
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    @Transactional
    @Override
    public Supplier updateSupplier(Long id, Supplier supplier) {
        if (supplierRepository.existsById(id)) {
            supplier.setSupplierId(id);
            return supplierRepository.save(supplier);
        }
        return null;
    }
    @Transactional
    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
