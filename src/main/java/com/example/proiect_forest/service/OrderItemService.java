package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Order;
import com.example.proiect_forest.model.OrderItem;
import com.example.proiect_forest.model.Product;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderItemService {


    @Transactional
    void saveOrderItem(OrderItem orderItem, Order order, Product product);

    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
    void updateOrderItem(Long id, OrderItem orderItem);
    void deleteOrderItem(Long id);
}
