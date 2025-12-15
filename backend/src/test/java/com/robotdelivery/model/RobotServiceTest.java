package com.robotdelivery.model;

import com.robotdelivery.model.Robot.RobotStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RobotModelTest {

    @Test
    void testRobotBuilderAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Long testId = 10L;

        Robot robot = new Robot.RobotBuilder()
                .robotId("R50")
                .name("Zeta")
                .status(RobotStatus.DELIVERING)
                .position(50, 100)
                .batteryLevel(75)
                .lastHeartbeat(now)
                .build();

        Robot defaultRobot = new Robot();
        assertNotNull(defaultRobot);

        robot.setId(testId);

        assertEquals(testId, robot.getId());
        assertEquals("R50", robot.getRobotId());
        assertEquals("Zeta", robot.getName());
        assertEquals(RobotStatus.DELIVERING, robot.getStatus());
        assertEquals(50, robot.getCurrentX());
        assertEquals(100, robot.getCurrentY());
        assertEquals(75, robot.getBatteryLevel());
        assertEquals(now, robot.getLastHeartbeat());

        assertEquals(RobotStatus.IDLE, RobotStatus.valueOf("IDLE"));
        assertEquals(RobotStatus.MOVING, RobotStatus.valueOf("MOVING"));
        assertEquals(RobotStatus.CHARGING, RobotStatus.valueOf("CHARGING"));
        assertEquals(RobotStatus.ERROR, RobotStatus.valueOf("ERROR"));
    }

    @Test
    void testRobotSetters() {
        Robot robot = new Robot.RobotBuilder().build();
        LocalDateTime newTime = LocalDateTime.now().plusHours(1);
        Long newId = 20L;

        robot.setId(newId);
        robot.setRobotId("R101");
        robot.setName("Alpha");
        robot.setStatus(RobotStatus.CHARGING);
        robot.setCurrentX(200);
        robot.setCurrentY(300);
        robot.setBatteryLevel(99);
        robot.setLastHeartbeat(newTime);

        assertEquals(newId, robot.getId());
        assertEquals("R101", robot.getRobotId());
        assertEquals("Alpha", robot.getName());
        assertEquals(RobotStatus.CHARGING, robot.getStatus());
        assertEquals(200, robot.getCurrentX());
        assertEquals(300, robot.getCurrentY());
        assertEquals(99, robot.getBatteryLevel());
        assertEquals(newTime, robot.getLastHeartbeat());
    }

}