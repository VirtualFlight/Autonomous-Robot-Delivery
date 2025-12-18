/*
 * Author: Jahiem Allen
 * TCP Server for receiving sensor data from Raspberry Pi
 * Runs on port 65432 to match Python client
 */

package com.robotdelivery.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.service.RobotService;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TCPServer {

    private final RobotService robotService;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private volatile boolean running = false;

    private static final int TCP_PORT = 65432;
    private static final double OBSTACLE_THRESHOLD = 30.0; // cm

    public TCPServer(RobotService robotService, EventPublisher eventPublisher) {
        this.robotService = robotService;
        this.eventPublisher = eventPublisher;
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void start() {
        executorService = Executors.newFixedThreadPool(10);
        running = true;

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(TCP_PORT);
                eventPublisher.onTCPConnectionEstablished("Connected to TCP server", TCP_PORT);

                while (running) {
                    Socket clientSocket = serverSocket.accept();
                    executorService.submit(() -> handleClient(clientSocket));
                }
            } catch (IOException e) {
                if (running) {
                    eventPublisher.onTCPConnectionLost("Connection Lost!");
                }
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String line = in.readLine();

            if (line != null && !line.isEmpty()) {
                System.out.println("[TCP] Received: " + line);

                try {
                    Map<String, Object> sensorData = parseJsonData(line);
                    robotService.processSensorData(sensorData);
                    out.println("OK");
                } catch (Exception e) {
                    System.err.println("[TCP] Parse error: " + e.getMessage());
                    out.println("ERROR: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("[TCP] Client error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                // ignore for now
            }
        }
    }


    private Map<String, Object> parseJsonData(String jsonData) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> rawData = objectMapper.readValue(jsonData, Map.class);

        double distanceRight = getDoubleValue(rawData, "distance_right");
        double distanceMiddle = getDoubleValue(rawData, "distance_middle");
        double distanceLeft = getDoubleValue(rawData, "distance_left");


        @SuppressWarnings("unchecked")
        List<Number> imuData = (List<Number>) rawData.get("imu");

        Map<String, Object> sensorData = new HashMap<>();

        // TESTING -- CHANGE LATER
        sensorData.put("robotId", "ROBOT_001");

        sensorData.put("leftObstacle", distanceLeft < OBSTACLE_THRESHOLD);
        sensorData.put("frontObstacle", distanceMiddle < OBSTACLE_THRESHOLD);
        sensorData.put("rightObstacle", distanceRight < OBSTACLE_THRESHOLD);

        sensorData.put("distanceLeft", distanceLeft);
        sensorData.put("distanceMiddle", distanceMiddle);
        sensorData.put("distanceRight", distanceRight);

        if (imuData != null && imuData.size() >= 6) {
            sensorData.put("accelX", imuData.get(0).doubleValue());
            sensorData.put("accelY", imuData.get(1).doubleValue());
            sensorData.put("accelZ", imuData.get(2).doubleValue());
            sensorData.put("gyroX", imuData.get(3).doubleValue());
            sensorData.put("gyroY", imuData.get(4).doubleValue());
            sensorData.put("gyroZ", imuData.get(5).doubleValue());
        }

        return sensorData;
    }

    private double getDoubleValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        throw new IllegalArgumentException("Invalid or missing value for: " + key);
    }

    @PreDestroy
    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (executorService != null) {
                executorService.shutdown();
            }
            System.out.println("[TCP] Server stopped");
        } catch (IOException e) {
            System.err.println("[TCP] Error stopping server: " + e.getMessage());
        }
    }
}