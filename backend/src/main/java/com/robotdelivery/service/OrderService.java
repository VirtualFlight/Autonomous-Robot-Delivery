/*
 * Author: Jahiem Allen
 * Integrates with delivery system for robot  -- important
 */

package com.robotdelivery.service;

import com.robotdelivery.factory.RestaurantFactory;
import com.robotdelivery.model.*;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repositorys.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantFactory restaurantFactory;
    private final EventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CustomerRepository customerRepository,
                        MenuItemRepository menuItemRepository,
                        RestaurantFactory restaurantFactory,
                        EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantFactory = restaurantFactory;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Order createOrder(Map<String, Object> request) {
        Long customerId = Long.valueOf(request.get("customerId").toString());
        String deliveryAddress = (String) request.get("deliveryAddress");
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = restaurantFactory.createOrder(customer, deliveryAddress);
        order = orderRepository.save(order);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map<String, Object> itemData : items) {
            Long menuItemId = Long.valueOf(itemData.get("menuItemId").toString());
            Integer quantity = (Integer) itemData.get("quantity");

            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem orderItem = restaurantFactory.createOrderItem(order, menuItem, quantity);
            orderItemRepository.save(orderItem);

            BigDecimal itemTotal = menuItem.getPrice().multiply(new BigDecimal(quantity));
            totalPrice = totalPrice.add(itemTotal);
        }

        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);

        eventPublisher.notifyOrderCreated(order.getId(), customer.getEmail(), totalPrice);

        return order;
    }

    public List<Order> getOrderHistory(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return orderRepository.findByCustomerOrderByCreatedAtDesc(customer);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);

        if (newStatus == Order.OrderStatus.DELIVERED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        order = orderRepository.save(order);

        // Notify observers
        eventPublisher.notifyOrderStatusChanged(orderId, newStatus.toString());

        return order;
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order linkDelivery(Long orderId, Delivery delivery) {
        Order order = getOrderById(orderId);
        order.setDelivery(delivery);
        order.setStatus(Order.OrderStatus.OUT_FOR_DELIVERY);
        return orderRepository.save(order);
    }
}