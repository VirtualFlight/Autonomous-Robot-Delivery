package com.robotdelivery.service;

import com.robotdelivery.factory.EntityFactory;
import com.robotdelivery.model.Robot;
import com.robotdelivery.model.SensorData;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repository.RobotRepository;
import com.robotdelivery.repository.SensorDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RobotServiceTest {

    @Mock
    private RobotRepository robotRepository;
    @Mock
    private SensorDataRepository sensorDataRepository;
    @Mock
    private EntityFactory entityFactory;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private RobotService robotService;

    private Robot mockRobot;
    private SensorData mockSensorData;

    @BeforeEach
    void setUp() {
        mockRobot = new Robot.RobotBuilder()
                .robotId("R1")
                .name("Wally")
                .status(Robot.RobotStatus.IDLE)
                .build();
        mockSensorData = new SensorData();
    }

    @Test
    void registerRobot_success() {
        when(entityFactory.createRobot("R1", "Wally")).thenReturn(mockRobot);
        when(robotRepository.save(mockRobot)).thenReturn(mockRobot);

        Robot registeredRobot = robotService.registerRobot("R1", "Wally");

        assertNotNull(registeredRobot);
        assertEquals("R1", registeredRobot.getRobotId());

        verify(eventPublisher, times(1)).notifyRobotRegistered("R1", "Wally");
        verify(robotRepository, times(1)).save(mockRobot);
    }

    @Test
    void processSensorData_robotFound_updatesAndNotifies() {
        Map<String, Object> sensorDataMap = new HashMap<>();
        sensorDataMap.put("robotId", "R1");
        sensorDataMap.put("positionX", 10);
        sensorDataMap.put("positionY", 20);
        sensorDataMap.put("batteryLevel", 95);

        when(robotRepository.findByRobotId("R1")).thenReturn(Optional.of(mockRobot));
        when(entityFactory.createSensorData(eq(mockRobot), any(Map.class))).thenReturn(mockSensorData);
        when(sensorDataRepository.save(mockSensorData)).thenReturn(mockSensorData);

        robotService.processSensorData(sensorDataMap);

        assertEquals(10, mockRobot.getCurrentX());
        assertEquals(95, mockRobot.getBatteryLevel());
        verify(robotRepository, times(1)).save(mockRobot);
        verify(sensorDataRepository, times(1)).save(mockSensorData);

        verify(eventPublisher, times(1)).notifySensorDataReceived("R1", 10, 20);
    }

    @Test
    void processSensorData_robotNotFound_throwsException() {
        Map<String, Object> sensorDataMap = new HashMap<>();
        sensorDataMap.put("robotId", "R99");
        when(robotRepository.findByRobotId("R99")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                robotService.processSensorData(sensorDataMap)
        );

        verify(robotRepository, never()).save(any(Robot.class));
        verify(eventPublisher, never()).notifySensorDataReceived(any(), anyInt(), anyInt());
    }
}