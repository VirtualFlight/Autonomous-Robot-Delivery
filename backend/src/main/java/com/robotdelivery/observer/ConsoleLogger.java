/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.observer;

import org.springframework.stereotype.Component;
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
        System.out.println("[" + timestamp() + "]  ROBOT REGISTERED: " + robotId + " (" + name + ")");
    }

    @Override
    public void onSensorDataReceived(String robotId, int x, int y) {
        System.out.println("[" + timestamp() + "] SENSOR DATA: Robot " + robotId +
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
        System.out.println("[" + timestamp() + "] ERROR: " + message);
    }
}