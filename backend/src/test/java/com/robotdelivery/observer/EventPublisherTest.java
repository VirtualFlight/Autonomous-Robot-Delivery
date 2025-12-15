package com.robotdelivery.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventPublisherTest {

    private EventPublisher eventPublisher;

    @Mock
    private LoggerObserver mockObserver;

    @BeforeEach
    void setUp() {
        eventPublisher = new EventPublisher();
        eventPublisher.addObserver(mockObserver);
    }

    @Test
    void addObserver_and_removeObserver_works() {
        eventPublisher.removeObserver(mockObserver);

        eventPublisher.notifyRobotRegistered("R1", "Test");
        verify(mockObserver, never()).onRobotRegistered(anyString(), anyString());

        eventPublisher.addObserver(mockObserver);
        eventPublisher.notifyRobotRegistered("R1", "Test");
        verify(mockObserver, times(1)).onRobotRegistered("R1", "Test");
    }

    @Test
    void notifyRobotRegistered_callsObserver() {
        eventPublisher.notifyRobotRegistered("R1", "Wally");

        verify(mockObserver, times(1)).onRobotRegistered("R1", "Wally");
    }

    @Test
    void notifySensorDataReceived_callsObserver() {
        eventPublisher.notifySensorDataReceived("R2", 10, 20);

        verify(mockObserver, times(1)).onSensorDataReceived("R2", 10, 20);
    }

    @Test
    void notifyDeliveryCreated_callsObserver() {
        Long deliveryId = 1L;
        eventPublisher.notifyDeliveryCreated(deliveryId, "R3");

        verify(mockObserver, times(1)).onDeliveryCreated(deliveryId, "R3");
    }

    @Test
    void notifyDeliveryCompleted_callsObserver() {
        Long deliveryId = 2L;
        eventPublisher.notifyDeliveryCompleted(deliveryId);

        verify(mockObserver, times(1)).onDeliveryCompleted(deliveryId);
    }

    @Test
    void notifyError_callsObserver() {
        String message = "Battery low";
        eventPublisher.notifyError(message);

        verify(mockObserver, times(1)).onError(message);
    }
}