/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.service;

import com.robotdelivery.factory.EntityFactory;
import com.robotdelivery.model.Robot;
import com.robotdelivery.model.SensorData;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repositorys.RobotRepository;
import com.robotdelivery.repositorys.SensorDataRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RobotService {

    private final RobotRepository robotRepository;
    private final SensorDataRepository sensorDataRepository;
    private final EntityFactory entityFactory;
    private final EventPublisher eventPublisher;

    public RobotService(RobotRepository robotRepository,
                        SensorDataRepository sensorDataRepository,
                        EntityFactory entityFactory,
                        EventPublisher eventPublisher) {
        this.robotRepository = robotRepository;
        this.sensorDataRepository = sensorDataRepository;
        this.entityFactory = entityFactory;
        this.eventPublisher = eventPublisher;
    }

    public Robot registerRobot(String robotId, String name) {
        Robot robot = entityFactory.createRobot(robotId, name);
        robot = robotRepository.save(robot);

        eventPublisher.notifyRobotRegistered(robotId, name);

        return robot;
    }

    public void processSensorData(Map<String, Object> sensorData) {
        String robotId = (String) sensorData.get("robotId");

        Robot robot = robotRepository.findByRobotId(robotId)
                .orElseGet(() -> {
                    System.out.println("[RobotService] Robot not found, auto-registering: " + robotId);
                    return registerRobot(robotId, robotId);
                });

        Integer x = getIntegerValue(sensorData, "positionX");
        Integer y = getIntegerValue(sensorData, "positionY");
        Integer battery = getIntegerValue(sensorData, "batteryLevel");

        if (x != null) robot.setCurrentX(x);
        if (y != null) robot.setCurrentY(y);
        if (battery != null) robot.setBatteryLevel(battery);

        robot.setLastHeartbeat(LocalDateTime.now());
        robotRepository.save(robot);

        SensorData data = entityFactory.createSensorData(robot, sensorData);
        sensorDataRepository.save(data);

        String position = (x != null && y != null) ?
                String.format("(%d,%d)", x, y) : "position unknown";
        eventPublisher.notifySensorDataReceived(robotId, x != null ? x : 0, y != null ? y : 0);

        System.out.println("[RobotService] Processed data for " + robotId + " at " + position);
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

    public Robot getRobot(String robotId) {
        return robotRepository.findByRobotId(robotId)
                .orElseThrow(() -> new RuntimeException("Robot not found"));
    }

    public List<Robot> getAllRobots() {
        return robotRepository.findAll();
    }

    public List<SensorData> getSensorHistory(String robotId) {
        Robot robot = getRobot(robotId);
        return sensorDataRepository.findByRobotOrderByTimestampDesc(robot);
    }
}