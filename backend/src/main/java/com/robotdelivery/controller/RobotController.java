/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.controller;

import com.robotdelivery.model.Robot;
import com.robotdelivery.model.SensorData;
import com.robotdelivery.service.RobotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/robots")
@CrossOrigin(origins = "*")
public class RobotController {

    private final RobotService robotService;

    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @PostMapping("/register")
    public ResponseEntity<Robot> registerRobot(@RequestBody Map<String, String> request) {
        String robotId = request.get("robotId");
        String name = request.get("name");
        Robot robot = robotService.registerRobot(robotId, name);
        return ResponseEntity.ok(robot);
    }

    @PostMapping("/data")
    public ResponseEntity<String> receiveSensorData(@RequestBody Map<String, Object> sensorData) {
        robotService.processSensorData(sensorData);
        return ResponseEntity.ok("Data received");
    }

    @GetMapping("/{robotId}")
    public ResponseEntity<Robot> getRobot(@PathVariable String robotId) {
        return ResponseEntity.ok(robotService.getRobot(robotId));
    }

    @GetMapping
    public ResponseEntity<List<Robot>> getAllRobots() {
        return ResponseEntity.ok(robotService.getAllRobots());
    }

    @GetMapping("/{robotId}/sensors")
    public ResponseEntity<List<SensorData>> getSensorHistory(@PathVariable String robotId) {
        return ResponseEntity.ok(robotService.getSensorHistory(robotId));
    }
}