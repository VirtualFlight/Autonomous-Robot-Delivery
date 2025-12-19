/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.factory;

import com.robotdelivery.model.Delivery;
import com.robotdelivery.model.Robot;
import com.robotdelivery.model.SensorData;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class EntityFactory {

    private final RestaurantFactory restaurantFactory;

    public EntityFactory(RestaurantFactory restaurantFactory) {
        this.restaurantFactory = restaurantFactory;
    }

    public Robot createRobot(String robotId, String name) {
        return new Robot.RobotBuilder()
                .robotId(robotId)
                .name(name)
                .status(Robot.RobotStatus.IDLE)
                .position(0, 0)
                .batteryLevel(100)
                .build();
    }

    public SensorData createSensorData(Robot robot, Map<String, Object> data) {
        SensorData.SensorBuilder builder = new SensorData.SensorBuilder()
                .robot(robot);

        Integer x = getIntegerValue(data, "positionX");
        Integer y = getIntegerValue(data, "positionY");
        if (x != null || y != null) {
            builder.position(x, y);
        }

        builder.obstacles(
                getBooleanValue(data, "leftObstacle"),
                getBooleanValue(data, "frontObstacle"),
                getBooleanValue(data, "rightObstacle")
        );

        Double distLeft = getDoubleValue(data, "distanceLeft");
        Double distMiddle = getDoubleValue(data, "distanceMiddle");
        Double distRight = getDoubleValue(data, "distanceRight");
        if (distLeft != null || distMiddle != null || distRight != null) {
            builder.distances(distLeft, distMiddle, distRight);
        }

        Double accelX = getDoubleValue(data, "accelX");
        Double accelY = getDoubleValue(data, "accelY");
        Double accelZ = getDoubleValue(data, "accelZ");
        if (accelX != null || accelY != null || accelZ != null) {
            builder.accelerometer(accelX, accelY, accelZ);
        }

        Double gyroX = getDoubleValue(data, "gyroX");
        Double gyroY = getDoubleValue(data, "gyroY");
        Double gyroZ = getDoubleValue(data, "gyroZ");
        if (gyroX != null || gyroY != null || gyroZ != null) {
            builder.gyroscope(gyroX, gyroY, gyroZ);
        }

        Integer battery = getIntegerValue(data, "batteryLevel");
        if (battery != null) {
            builder.batteryLevel(battery);
        }

        return builder.build();
    }

    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Number) return ((Number) value).intValue();
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double getDoubleValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Double) return (Double) value;
        if (value instanceof Number) return ((Number) value).doubleValue();
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean getBooleanValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return false;
        if (value instanceof Boolean) return (Boolean) value;
        return Boolean.parseBoolean(value.toString());
    }

    public Delivery createDelivery(Robot robot, Integer destX, Integer destY) {
        return new Delivery.DeliveryBuilder()
                .robot(robot)
                .destination(destX, destY)
                .status(Delivery.DeliveryStatus.PENDING)
                .build();
    }

    public Robot createRobotWithPosition(String robotId, Integer x, Integer y) {
        return new Robot.RobotBuilder()
                .robotId(robotId)
                .position(x, y)
                .status(Robot.RobotStatus.IDLE)
                .build();
    }
}