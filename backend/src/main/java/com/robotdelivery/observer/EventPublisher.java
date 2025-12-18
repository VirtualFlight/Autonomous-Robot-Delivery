/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.observer;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventPublisher {

    private final List<LoggerObserver> observers = new ArrayList<>();

    public void addObserver(LoggerObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LoggerObserver observer) {
        observers.remove(observer);
    }

    public void notifyRobotRegistered(String robotId, String name) {
        for (LoggerObserver observer : observers) {
            observer.onRobotRegistered(robotId, name);
        }
    }

    public void notifySensorDataReceived(String robotId, int x, int y) {
        for (LoggerObserver observer : observers) {
            observer.onSensorDataReceived(robotId, x, y);
        }
    }

    public void notifyDeliveryCreated(Long deliveryId, String robotId) {
        for (LoggerObserver observer : observers) {
            observer.onDeliveryCreated(deliveryId, robotId);
        }
    }

    public void notifyDeliveryCompleted(Long deliveryId) {
        for (LoggerObserver observer : observers) {
            observer.onDeliveryCompleted(deliveryId);
        }
    }

    public void notifyError(String message) {
        for (LoggerObserver observer : observers) {
            observer.onError(message);
        }
    }

    public void notifyCustomerRegistered(String email, String name) {
        for (LoggerObserver observer : observers) {
            observer.onCustomerRegistered(email, name);
        }
    }

    public void notifyCustomerLogin(String email) {
        for (LoggerObserver observer : observers) {
            observer.onCustomerLogin(email);
        }
    }

    public void notifyProfileUpdated(String email) {
        for (LoggerObserver observer : observers) {
            observer.onProfileUpdated(email);
        }
    }

    public void notifyOrderCreated(Long orderId, String customerEmail, BigDecimal total) {
        for (LoggerObserver observer : observers) {
            observer.onOrderCreated(orderId, customerEmail, total);
        }
    }

    public void notifyOrderStatusChanged(Long orderId, String status) {
        for (LoggerObserver observer : observers) {
            observer.onOrderStatusChanged(orderId, status);
        }
    }

    public void notifyMenuItemAdded(String itemName) {
        for (LoggerObserver observer : observers) {
            observer.onMenuItemAdded(itemName);
        }
    }

    public void onTCPConnectionEstablished(String message, int port) {
        for (LoggerObserver observer : observers) {
            observer.onTCPConnectionEstablished(message, port);
        }
    }
    public void onTCPConnectionLost(String message) {
        for (LoggerObserver observer : observers) {
            observer.onTCPConnectionLost(message);
        }
    }
}