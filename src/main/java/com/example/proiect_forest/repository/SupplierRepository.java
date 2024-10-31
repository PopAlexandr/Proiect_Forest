package com.example.proiect_forest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.proiect_forest.model.Supplier;

import java.util.List;
import java.util.Optional;

@Repository

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
