/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.observer;

import java.math.BigDecimal;

public interface LoggerObserver {
    void onRobotRegistered(String robotId, String name);
    void onSensorDataReceived(String robotId, int x, int y);
    void onDeliveryCreated(Long deliveryId, String robotId);
    void onDeliveryCompleted(Long deliveryId);
    void onError(String message);
    void onTCPConnectionLost(String message);
    void onTCPConnectionEstablished(String message, int port);

    void onCustomerRegistered(String email, String name);
    void onCustomerLogin(String email);
    void onProfileUpdated(String email);
    void onOrderCreated(Long orderId, String customerEmail, BigDecimal total);
    void onOrderStatusChanged(Long orderId, String status);
    void onMenuItemAdded(String itemName);
}