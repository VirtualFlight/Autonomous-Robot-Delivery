/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.observer;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ConsoleLogger implements LoggerObserver {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String timestamp() {
        return LocalDateTime.now().format(formatter);
    }

    @Override
    public void onRobotRegistered(String robotId, String name) {
        System.out.println("[" + timestamp() + "] ROBOT REGISTERED: " + robotId + " (" + name + ")");
    }

    @Override
    public void onSensorDataReceived(String robotId, int x, int y) {
        System.out.println("[" + timestamp() + "]  SENSOR DATA:  Robot " + robotId +
                " at position (" + x + "," + y + ")");
    }

    @Override
    public void onDeliveryCreated(Long deliveryId, String robotId) {
        System.out.println("[" + timestamp() + "] DELIVERY CREATED: ID=" + deliveryId +
                " assigned to " + robotId);
    }

    @Override
    public void onDeliveryCompleted(Long deliveryId) {
        System.out.println("[" + timestamp() + "] DELIVERY COMPLETED: ID=" + deliveryId);
    }

    @Override
    public void onError(String message) {
        System.out.println("[" + timestamp() + "]   ERROR: " + message);
    }

    @Override
    public void onCustomerRegistered(String email, String name) {
        System.out.println("[" + timestamp() + "] CUSTOMER REGISTERED: " + email + " (" + name + ")");
    }

    @Override
    public void onCustomerLogin(String email) {
        System.out.println("[" + timestamp() + "] LOGIN: " + email);
    }

    @Override
    public void onProfileUpdated(String email) {
        System.out.println("[" + timestamp() + "] PROFILE UPDATED: " + email);
    }

    @Override
    public void onOrderCreated(Long orderId, String customerEmail, BigDecimal total) {
        System.out.println("[" + timestamp() + "]  ORDER CREATED: ID=" + orderId +
                " Customer=" + customerEmail + " Total=$" + total);
    }

    @Override
    public void onOrderStatusChanged(Long orderId, String status) {
        System.out.println("[" + timestamp() + "] RDER STATUS: ID=" + orderId + " -> " + status);
    }

    @Override
    public void onMenuItemAdded(String itemName) {
        System.out.println("[" + timestamp() + "] MENU ITEM ADDED: " + itemName);
    }

    @Override
    public void onTCPConnectionEstablished(String message, int port) {
        System.out.println("[" + timestamp() + "] TCP CONNECTION ON: " + port + message);
    }
    @Override
    public void onTCPConnectionLost(String message) {
        System.out.println("[" + timestamp() + "] TCP CONNECTION LOST: " + message);
    }

    @Override
    public void onCustomerRegistered(String email, String name) {
        System.out.println("[" + timestamp() + "] CUSTOMER REGISTERED: " + email + " (" + name + ")");
    }

    @Override
    public void onCustomerLogin(String email) {
        System.out.println("[" + timestamp() + "] LOGIN: " + email);
    }

    @Override
    public void onProfileUpdated(String email) {
        System.out.println("[" + timestamp() + "] PROFILE UPDATED: " + email);
    }

    @Override
    public void onOrderCreated(Long orderId, String customerEmail, BigDecimal total) {
        System.out.println("[" + timestamp() + "]  ORDER CREATED: ID=" + orderId +
                " Customer=" + customerEmail + " Total=$" + total);
    }

    @Override
    public void onOrderStatusChanged(Long orderId, String status) {
        System.out.println("[" + timestamp() + "] RDER STATUS: ID=" + orderId + " -> " + status);
    }

    @Override
    public void onMenuItemAdded(String itemName) {
        System.out.println("[" + timestamp() + "] MENU ITEM ADDED: " + itemName);
    }
}