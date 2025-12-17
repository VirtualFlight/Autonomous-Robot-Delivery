/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal priceAtOrder;

    private String specialRequests;

    public OrderItem() {}

    private OrderItem(OrderItemBuilder builder) {
        this.order = builder.order;
        this.menuItem = builder.menuItem;
        this.quantity = builder.quantity;
        this.priceAtOrder = builder.priceAtOrder;
        this.specialRequests = builder.specialRequests;
    }

    public static class OrderItemBuilder {
        private Order order;
        private MenuItem menuItem;
        private Integer quantity = 1;
        private BigDecimal priceAtOrder;
        private String specialRequests;

        public OrderItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder menuItem(MenuItem menuItem) {
            this.menuItem = menuItem;
            return this;
        }

        public OrderItemBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder priceAtOrder(BigDecimal priceAtOrder) {
            this.priceAtOrder = priceAtOrder;
            return this;
        }

        public OrderItemBuilder specialRequests(String specialRequests) {
            this.specialRequests = specialRequests;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPriceAtOrder() { return priceAtOrder; }
    public void setPriceAtOrder(BigDecimal priceAtOrder) { this.priceAtOrder = priceAtOrder; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
}