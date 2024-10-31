package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Customer;
import com.example.proiect_forest.model.Order;
import com.example.proiect_forest.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public void saveOrder(Order order, Customer customer) {
        order.setCustomer(customer);


        customer.getOrders().add(order);


        orderRepository.save(order);
    }
    @Transactional
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Transactional
    @Override
    public void updateOrder(Long id, Order order) {
        if (orderRepository.existsById(id)) {
            order.setOrderId(id);
             orderRepository.save(order);
        }

    }

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
