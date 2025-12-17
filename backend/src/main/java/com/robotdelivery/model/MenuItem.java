/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private String category;
    private Boolean available;
    private String imageUrl;

    public MenuItem() {}

    private MenuItem(MenuItemBuilder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.category = builder.category;
        this.available = builder.available;
        this.imageUrl = builder.imageUrl;
    }

    public static class MenuItemBuilder {
        private String name;
        private String description;
        private BigDecimal price;
        private String category = "Other";
        private Boolean available = true;
        private String imageUrl;

        public MenuItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MenuItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MenuItemBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MenuItemBuilder category(String category) {
            this.category = category;
            return this;
        }

        public MenuItemBuilder available(Boolean available) {
            this.available = available;
            return this;
        }

        public MenuItemBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(this);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}