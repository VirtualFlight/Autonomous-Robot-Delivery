package com.robotdelivery.service;

import com.robotdelivery.factory.EntityFactory;
import com.robotdelivery.model.Delivery;
import com.robotdelivery.model.Delivery.DeliveryStatus;
import com.robotdelivery.model.Robot;
import com.robotdelivery.model.Robot.RobotStatus;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repository.DeliveryRepository;
import com.robotdelivery.repository.RobotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private RobotRepository robotRepository;
    @Mock
    private EntityFactory entityFactory;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private DeliveryService deliveryService;

    private Robot idleRobot;
    private Delivery newDelivery;

    @BeforeEach
    void setUp() {
        idleRobot = new Robot.RobotBuilder()
                .robotId("R2")
                .name("Eve")
                .status(Robot.RobotStatus.IDLE)
                .build();

        newDelivery = new Delivery();
        newDelivery.setId(1L);
        newDelivery.setRobot(idleRobot);
        newDelivery.setStatus(DeliveryStatus.PENDING);
    }

    @Test
    void createDelivery_idleRobot_success() {
        Map<String, Object> request = new HashMap<>();
        request.put("robotId", "R2");
        request.put("destinationX", 50);
        request.put("destinationY", 50);

        when(robotRepository.findByRobotId("R2")).thenReturn(Optional.of(idleRobot));
        when(entityFactory.createDelivery(eq(idleRobot), anyInt(), anyInt())).thenReturn(newDelivery);
        when(deliveryRepository.save(newDelivery)).thenReturn(newDelivery);

        Delivery created = deliveryService.createDelivery(request);

        assertEquals(RobotStatus.DELIVERING, idleRobot.getStatus());
        assertEquals(newDelivery, created);

        verify(robotRepository, times(1)).save(idleRobot);
        verify(deliveryRepository, times(1)).save(newDelivery);
        verify(eventPublisher, times(1)).notifyDeliveryCreated(1L, "R2");
    }

    @Test
    void createDelivery_busyRobot_throwsExceptionAndNotifiesError() {
        Robot busyRobot = new Robot.RobotBuilder()
                .robotId("R3")
                .name("Wall-E")
                .status(Robot.RobotStatus.DELIVERING)
                .build();
        busyRobot.setStatus(RobotStatus.DELIVERING);
        Map<String, Object> request = Map.of("robotId", "R3", "destinationX", 50, "destinationY", 50);

        when(robotRepository.findByRobotId("R3")).thenReturn(Optional.of(busyRobot));

        assertThrows(RuntimeException.class, () -> deliveryService.createDelivery(request), "Robot is busy");

        verify(eventPublisher, times(1)).notifyError("Robot R3 is busy");
        verify(deliveryRepository, never()).save(any(Delivery.class));
    }

    @Test
    void completeDelivery_success_updatesStatusesAndNotifies() {
        Delivery inProgressDelivery = newDelivery;
        inProgressDelivery.setStatus(DeliveryStatus.IN_PROGRESS);

        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(inProgressDelivery));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(inProgressDelivery);
        when(robotRepository.save(any(Robot.class))).thenReturn(idleRobot); // Mock robot save

        deliveryService.completeDelivery(1L);

        assertEquals(DeliveryStatus.COMPLETED, inProgressDelivery.getStatus());
        assertNotNull(inProgressDelivery.getEndTime());
        assertEquals(RobotStatus.IDLE, inProgressDelivery.getRobot().getStatus());

        verify(deliveryRepository, times(1)).save(inProgressDelivery);
        verify(robotRepository, times(1)).save(inProgressDelivery.getRobot());
        verify(eventPublisher, times(1)).notifyDeliveryCompleted(1L);
    }
}