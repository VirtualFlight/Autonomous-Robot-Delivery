/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.observer;

public interface LoggerObserver {
    void onRobotRegistered(String robotId, String name);
    void onSensorDataReceived(String robotId, int x, int y);
    void onDeliveryCreated(Long deliveryId, String robotId);
    void onDeliveryCompleted(Long deliveryId);
    void onError(String message);
}