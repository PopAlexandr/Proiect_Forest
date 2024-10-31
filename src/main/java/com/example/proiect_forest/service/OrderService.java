package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Customer;
import com.example.proiect_forest.model.Order;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order, Customer customer);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    void updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}
