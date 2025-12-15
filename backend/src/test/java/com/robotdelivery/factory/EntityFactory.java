package com.robotdelivery.factory;

import com.robotdelivery.model.Delivery;
import com.robotdelivery.model.Robot;
import com.robotdelivery.model.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EntityFactoryTest {

    private EntityFactory entityFactory;
    private Robot mockRobot;

    @BeforeEach
    void setUp() {
        entityFactory = new EntityFactory();

        mockRobot = new Robot.RobotBuilder()
                .robotId("R1")
                .name("TestBot")
                .status(Robot.RobotStatus.IDLE)
                .position(5, 10)
                .batteryLevel(95)
                .lastHeartbeat(LocalDateTime.now())
                .build();
    }

    @Test
    void createRobot_initializesCorrectly() {
        Robot robot = entityFactory.createRobot("R100", "NewBot");

        assertNotNull(robot);
        assertEquals("R100", robot.getRobotId());
        assertEquals("NewBot", robot.getName());
        assertEquals(Robot.RobotStatus.IDLE, robot.getStatus());
        assertEquals(100, robot.getBatteryLevel());
        assertEquals(0, robot.getCurrentX());
        assertEquals(0, robot.getCurrentY());
        assertNotNull(robot.getLastHeartbeat());
    }

    @Test
    void createSensorData_mapsAllFieldsCorrectly() {
        Map<String, Object> sensorRequest = Map.of(
                "positionX", 50,
                "positionY", 60,
                "leftObstacle", true,
                "frontObstacle", false,
                "rightObstacle", true,
                "batteryLevel", 85
        );

        SensorData sensorData = entityFactory.createSensorData(mockRobot, sensorRequest);

        assertNotNull(sensorData);
        assertEquals(mockRobot, sensorData.getRobot());
        assertEquals(50, sensorData.getPositionX());
        assertEquals(60, sensorData.getPositionY());
        assertTrue(sensorData.getLeftObstacle());
        assertFalse(sensorData.getFrontObstacle());
        assertTrue(sensorData.getRightObstacle());
        assertEquals(85, sensorData.getBatteryLevel());
    }

    @Test
    void createDelivery_initializesCorrectly() {
        Integer destX = 100;
        Integer destY = 200;

        Delivery delivery = entityFactory.createDelivery(mockRobot, destX, destY);

        assertNotNull(delivery);
        assertEquals(mockRobot, delivery.getRobot());
        assertEquals(5, delivery.getStartX());
        assertEquals(10, delivery.getStartY());
        assertEquals(destX, delivery.getDestinationX());
        assertEquals(destY, delivery.getDestinationY());
        assertEquals(Delivery.DeliveryStatus.PENDING, delivery.getStatus());
    }

    @Test
    void createRobotWithPosition_initializesCorrectly() {
        Robot robot = entityFactory.createRobotWithPosition("R200", "PosBot", 75, 90);

        assertNotNull(robot);
        assertEquals("R200", robot.getRobotId());
        assertEquals(75, robot.getCurrentX());
        assertEquals(90, robot.getCurrentY());
        assertEquals(Robot.RobotStatus.IDLE, robot.getStatus());
        assertEquals(100, robot.getBatteryLevel());
    }
}