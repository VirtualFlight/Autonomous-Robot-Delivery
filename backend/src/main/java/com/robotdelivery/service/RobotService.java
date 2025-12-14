/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.service;

import com.robotdelivery.factory.EntityFactory;
import com.robotdelivery.model.Robot;
import com.robotdelivery.model.SensorData;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repository.RobotRepository;
import com.robotdelivery.repository.SensorDataRepository;
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

    public void processSensorData(Map<String, Object> sensorDataMap) {
        String robotId = (String) sensorDataMap.get("robotId");
        if (robotId == null) throw new RuntimeException("robotId is missing");

        Integer x = toInteger(sensorDataMap.get("positionX"), 0);
        Integer y = toInteger(sensorDataMap.get("positionY"), 0);
        Integer battery = toInteger(sensorDataMap.get("batteryLevel"), 100);

        Boolean left = toBoolean(sensorDataMap.get("leftObstacle"), false);
        Boolean front = toBoolean(sensorDataMap.get("frontObstacle"), false);
        Boolean right = toBoolean(sensorDataMap.get("rightObstacle"), false);

        Robot robot = robotRepository.findByRobotId(robotId)
                .orElseThrow(() -> new RuntimeException("Robot not found"));

        robot.setCurrentX(x);
        robot.setCurrentY(y);
        robot.setBatteryLevel(battery);
        robot.setLastHeartbeat(LocalDateTime.now());
        robotRepository.save(robot);

        SensorData data = new SensorData.Builder()
                .robot(robot)
                .position(x, y)
                .obstacles(left, front, right)
                .batteryLevel(battery)
                .build();

        sensorDataRepository.save(data);
        eventPublisher.notifySensorDataReceived(robotId, x, y);
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

    private Integer toInteger(Object obj, int defaultVal) {
        if (obj == null) return defaultVal;
        if (obj instanceof Number) return ((Number) obj).intValue();
        try { return Integer.parseInt(obj.toString()); } catch (Exception e) { return defaultVal; }
    }

    private Boolean toBoolean(Object obj, boolean defaultVal) {
        if (obj == null) return defaultVal;
        if (obj instanceof Boolean) return (Boolean) obj;
        return Boolean.parseBoolean(obj.toString());
    }
}