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
        try {
            // Debug: Print what we received
            System.out.println("DEBUG - Request received: " + request);

            // Safely parse customerId
            Object customerIdObj = request.get("customerId");
            if (customerIdObj == null) {
                throw new RuntimeException("customerId is required");
            }
            Long customerId;
            if (customerIdObj instanceof Number) {
                customerId = ((Number) customerIdObj).longValue();
            } else if (customerIdObj instanceof String) {
                customerId = Long.parseLong((String) customerIdObj);
            } else {
                throw new RuntimeException("customerId must be a number or string");
            }

            String deliveryAddress = (String) request.get("deliveryAddress");
            if (deliveryAddress == null) {
                deliveryAddress = "Unknown Address";
            }

            // Safely parse items
            Object itemsObj = request.get("items");
            if (itemsObj == null || !(itemsObj instanceof List)) {
                throw new RuntimeException("items must be a list");
            }

            List<?> itemsList = (List<?>) itemsObj;
            System.out.println("DEBUG - Items list: " + itemsList);

            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

            Order order = restaurantFactory.createOrder(customer, deliveryAddress);
            order = orderRepository.save(order);

            BigDecimal totalPrice = BigDecimal.ZERO;

            for (Object itemObj : itemsList) {
                if (!(itemObj instanceof Map)) {
                    continue; // or throw exception
                }

                Map<?, ?> itemData = (Map<?, ?>) itemObj;

                // Safely parse menuItemId
                Object menuItemIdObj = itemData.get("menuItemId");
                if (menuItemIdObj == null) {
                    throw new RuntimeException("menuItemId is required for each item");
                }
                Long menuItemId;
                if (menuItemIdObj instanceof Number) {
                    menuItemId = ((Number) menuItemIdObj).longValue();
                } else if (menuItemIdObj instanceof String) {
                    menuItemId = Long.parseLong((String) menuItemIdObj);
                } else {
                    throw new RuntimeException("menuItemId must be a number or string");
                }

                // Safely parse quantity
                Object quantityObj = itemData.get("quantity");
                if (quantityObj == null) {
                    throw new RuntimeException("quantity is required for each item");
                }
                Integer quantity;
                if (quantityObj instanceof Number) {
                    quantity = ((Number) quantityObj).intValue();
                } else if (quantityObj instanceof String) {
                    quantity = Integer.parseInt((String) quantityObj);
                } else {
                    throw new RuntimeException("quantity must be a number or string");
                }

                MenuItem menuItem = menuItemRepository.findById(menuItemId)
                        .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + menuItemId));

                OrderItem orderItem = restaurantFactory.createOrderItem(order, menuItem, quantity);
                orderItemRepository.save(orderItem);

                BigDecimal itemTotal = menuItem.getPrice().multiply(new BigDecimal(quantity));
                totalPrice = totalPrice.add(itemTotal);
            }

            order.setTotalPrice(totalPrice);
            order = orderRepository.save(order);

            eventPublisher.notifyOrderCreated(order.getId(), customer.getEmail(), totalPrice);

            return order;

        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format in request: " + e.getMessage(), e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid data type in request: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating order: " + e.getMessage(), e);
        }
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