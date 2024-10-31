package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Customer;

import java.util.List;

public interface CustomerService {
    void saveCustomer(Customer customer);
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    void updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
}
