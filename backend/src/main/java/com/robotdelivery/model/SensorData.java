/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "robot_id")
    private Robot robot;

    private Integer positionX;
    private Integer positionY;
    private Boolean leftObstacle;
    private Boolean frontObstacle;
    private Boolean rightObstacle;
    private Integer batteryLevel;
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    public SensorData() {}

    private SensorData(Builder builder) {
        this.robot = builder.robot;
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
        this.leftObstacle = builder.leftObstacle;
        this.frontObstacle = builder.frontObstacle;
        this.rightObstacle = builder.rightObstacle;
        this.batteryLevel = builder.batteryLevel;
    }

    public static class Builder {
        private Robot robot;
        private Integer positionX;
        private Integer positionY;
        private Boolean leftObstacle = false;
        private Boolean frontObstacle = false;
        private Boolean rightObstacle = false;
        private Integer batteryLevel = 100;

        public Builder robot(Robot robot) {
            this.robot = robot;
            return this;
        }

        public Builder position(Integer x, Integer y) {
            this.positionX = x;
            this.positionY = y;
            return this;
        }

        public Builder obstacles(Boolean left, Boolean front, Boolean right) {
            this.leftObstacle = left;
            this.frontObstacle = front;
            this.rightObstacle = right;
            return this;
        }

        public Builder batteryLevel(Integer batteryLevel) {
            this.batteryLevel = batteryLevel;
            return this;
        }

        public SensorData build() {
            return new SensorData(this);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Robot getRobot() { return robot; }
    public void setRobot(Robot robot) { this.robot = robot; }

    public Integer getPositionX() { return positionX; }
    public void setPositionX(Integer positionX) { this.positionX = positionX; }

    public Integer getPositionY() { return positionY; }
    public void setPositionY(Integer positionY) { this.positionY = positionY; }

    public Boolean getLeftObstacle() { return leftObstacle; }
    public void setLeftObstacle(Boolean leftObstacle) { this.leftObstacle = leftObstacle; }

    public Boolean getFrontObstacle() { return frontObstacle; }
    public void setFrontObstacle(Boolean frontObstacle) { this.frontObstacle = frontObstacle; }

    public Boolean getRightObstacle() { return rightObstacle; }
    public void setRightObstacle(Boolean rightObstacle) { this.rightObstacle = rightObstacle; }

    public Integer getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(Integer batteryLevel) { this.batteryLevel = batteryLevel; }

    public LocalDateTime getTimestamp() { return timestamp; }
}