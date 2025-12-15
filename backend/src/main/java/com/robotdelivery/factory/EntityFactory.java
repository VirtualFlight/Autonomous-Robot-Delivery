/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.factory;

import com.robotdelivery.model.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class EntityFactory {

    public Robot createRobot(String robotId, String name) {
        return new Robot.RobotBuilder()
                .robotId(robotId)
                .name(name)
                .status(Robot.RobotStatus.IDLE)
                .position(0, 0)
                .batteryLevel(100)
                .lastHeartbeat(LocalDateTime.now())
                .build();
    }

    public SensorData createSensorData(Robot robot, Map<String, Object> sensorRequest) {
        Integer x = (Integer) sensorRequest.get("positionX");
        Integer y = (Integer) sensorRequest.get("positionY");
        Boolean left = (Boolean) sensorRequest.get("leftObstacle");
        Boolean front = (Boolean) sensorRequest.get("frontObstacle");
        Boolean right = (Boolean) sensorRequest.get("rightObstacle");
        Integer battery = (Integer) sensorRequest.get("batteryLevel");

        return new SensorData.SensorBuilder()
                .robot(robot)
                .position(x, y)
                .obstacles(left, front, right)
                .batteryLevel(battery)
                .build();
    }

    public Delivery createDelivery(Robot robot, Integer destX, Integer destY) {
        return new Delivery.DeliveryBuilder()
                .robot(robot)
                .start(robot.getCurrentX(), robot.getCurrentY())
                .destination(destX, destY)
                .status(Delivery.DeliveryStatus.PENDING)
                .build();
    }

    public Robot createRobotWithPosition(String robotId, String name, Integer x, Integer y) {
        return new Robot.RobotBuilder()
                .robotId(robotId)
                .name(name)
                .status(Robot.RobotStatus.IDLE)
                .position(x, y)
                .batteryLevel(100)
                .lastHeartbeat(LocalDateTime.now())
                .build();
    }
}