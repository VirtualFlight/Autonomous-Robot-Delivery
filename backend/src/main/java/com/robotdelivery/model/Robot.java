/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "robots")
public class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String robotId;
    private String name;

    @Enumerated(EnumType.STRING)
    private RobotStatus status;

    private Integer currentX;
    private Integer currentY;
    private Integer batteryLevel;
    private LocalDateTime lastHeartbeat;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Robot() {}

    private Robot(Builder builder) {
        this.robotId = builder.robotId;
        this.name = builder.name;
        this.status = builder.status;
        this.currentX = builder.currentX;
        this.currentY = builder.currentY;
        this.batteryLevel = builder.batteryLevel;
        this.lastHeartbeat = builder.lastHeartbeat;
    }

    public static class Builder {
        private String robotId;
        private String name;
        private RobotStatus status = RobotStatus.IDLE;
        private Integer currentX = 0;
        private Integer currentY = 0;
        private Integer batteryLevel = 100;
        private LocalDateTime lastHeartbeat = LocalDateTime.now();

        public Builder robotId(String robotId) {
            this.robotId = robotId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder status(RobotStatus status) {
            this.status = status;
            return this;
        }

        public Builder position(Integer x, Integer y) {
            this.currentX = x;
            this.currentY = y;
            return this;
        }

        public Builder batteryLevel(Integer batteryLevel) {
            this.batteryLevel = batteryLevel;
            return this;
        }

        public Builder lastHeartbeat(LocalDateTime lastHeartbeat) {
            this.lastHeartbeat = lastHeartbeat;
            return this;
        }

        public Robot build() {
            return new Robot(this);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRobotId() { return robotId; }
    public void setRobotId(String robotId) { this.robotId = robotId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public RobotStatus getStatus() { return status; }
    public void setStatus(RobotStatus status) { this.status = status; }

    public Integer getCurrentX() { return currentX; }
    public void setCurrentX(Integer currentX) { this.currentX = currentX; }

    public Integer getCurrentY() { return currentY; }
    public void setCurrentY(Integer currentY) { this.currentY = currentY; }

    public Integer getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(Integer batteryLevel) { this.batteryLevel = batteryLevel; }

    public LocalDateTime getLastHeartbeat() { return lastHeartbeat; }
    public void setLastHeartbeat(LocalDateTime lastHeartbeat) { this.lastHeartbeat = lastHeartbeat; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public enum RobotStatus {
        IDLE, MOVING, DELIVERING, CHARGING, ERROR
    }
}