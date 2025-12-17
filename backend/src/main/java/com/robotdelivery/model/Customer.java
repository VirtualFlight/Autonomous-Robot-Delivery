/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Customer() {}

    private Customer(CustomerBuilder builder) {
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
    }

    public static class CustomerBuilder {
        private String email;
        private String passwordHash;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String address;
        private String city;
        private String postalCode;

        public CustomerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public CustomerBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CustomerBuilder city(String city) {
            this.city = city;
            return this;
        }

        public CustomerBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
}