package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Customer;
import com.example.proiect_forest.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Transactional
    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
    @Transactional
    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    @Transactional
    @Override
    public void updateCustomer(Long id, Customer customer) {
        if (customerRepository.existsById(id)) {
            customer.setCustomerId(id);
             customerRepository.save(customer);
        }

    }
    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
