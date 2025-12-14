/*
 * Author: Jahiem Allen
 *
 */

package com.robotdelivery.observer;

import org.springframework.stereotype.Component;
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
}