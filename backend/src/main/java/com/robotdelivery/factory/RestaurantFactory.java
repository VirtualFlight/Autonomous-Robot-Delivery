/*
 * Author: Jahiem Allen
 *
 */

package com.robotdelivery.factory;

import com.robotdelivery.model.*;
import com.robotdelivery.util.PasswordUtil;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Map;

@Component
public class RestaurantFactory {

    private final PasswordUtil passwordUtil;

    public RestaurantFactory(PasswordUtil passwordUtil) {
        this.passwordUtil = passwordUtil;
    }


    public Customer createCustomer(Map<String, String> request) {
        String plainPassword = request.get("password");
        String hashedPassword = passwordUtil.hashPassword(plainPassword);

        return new Customer.CustomerBuilder()
                .email(request.get("email"))
                .passwordHash(hashedPassword)
                .firstName(request.get("firstName"))
                .lastName(request.get("lastName"))
                .phoneNumber(request.get("phoneNumber"))
                .address(request.get("address"))
                .city(request.get("city"))
                .postalCode(request.get("postalCode"))
                .build();
    }


    public MenuItem createMenuItem(Map<String, Object> request) {
        BigDecimal price = new BigDecimal(request.get("price").toString());

        return new MenuItem.MenuItemBuilder()
                .name((String) request.get("name"))
                .description((String) request.get("description"))
                .price(price)
                .category((String) request.get("category"))
                .available((Boolean) request.getOrDefault("available", true))
                .imageUrl((String) request.get("imageUrl"))
                .build();
    }


    public Order createOrder(Customer customer, String deliveryAddress) {
        return new Order.OrderBuilder()
                .customer(customer)
                .totalPrice(BigDecimal.ZERO)
                .status(Order.OrderStatus.PENDING)
                .deliveryAddress(deliveryAddress)
                .build();
    }

    public OrderItem createOrderItem(Order order, MenuItem menuItem, Integer quantity) {
        return new OrderItem.OrderItemBuilder()
                .order(order)
                .menuItem(menuItem)
                .quantity(quantity)
                .priceAtOrder(menuItem.getPrice())
                .build();
    }
}