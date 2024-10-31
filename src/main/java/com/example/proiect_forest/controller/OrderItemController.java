package com.example.proiect_forest.controller;

import com.example.proiect_forest.model.Order;
import com.example.proiect_forest.model.OrderItem;
import com.example.proiect_forest.model.Product;
import com.example.proiect_forest.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public void createOrderItem(@RequestBody OrderItem orderItem, Order order, Product product) {
         orderItemService.saveOrderItem(orderItem,order,product);
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @PutMapping("/{id}")
    public void updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItemService.updateOrderItem(id, orderItem);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
