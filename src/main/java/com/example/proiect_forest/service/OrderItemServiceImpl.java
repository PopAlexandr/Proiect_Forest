package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Order;
import com.example.proiect_forest.model.OrderItem;
import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Transactional
    @Override
    public void saveOrderItem(OrderItem orderItem, Order order, Product product) {
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        product.getOrderItems().add(orderItem);
        order.getOrderItems().add(orderItem);

        orderItemRepository.save(orderItem);

    }
    @Transactional
    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
    @Transactional
    @Override
    public void updateOrderItem(Long id, OrderItem orderItem) {
        if (orderItemRepository.existsById(id)) {
            orderItem.setOrderItemId(id);
             orderItemRepository.save(orderItem);
        }

    }
    @Transactional
    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
