/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.controller;

import com.robotdelivery.model.Order;
import com.robotdelivery.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrderHistory(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status);
        Order order = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status);
        List<Order> orders = orderService.getOrdersByStatus(orderStatus);
        return ResponseEntity.ok(orders);
    }
}