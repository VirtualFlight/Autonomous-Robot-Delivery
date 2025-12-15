package com.robotdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "robot_id", nullable = false)
    private Robot robot;

    @Column(name = "position_x", nullable = false)
    private Integer positionX;

    @Column(name = "position_y", nullable = false)
    private Integer positionY;

    @Column(nullable = false)
    private Boolean leftObstacle;

    @Column(nullable = false)
    private Boolean frontObstacle;

    @Column(nullable = false)
    private Boolean rightObstacle;

    @Column(nullable = false)
    private Integer batteryLevel;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) timestamp = LocalDateTime.now();
    }

    public SensorData() {}

    private SensorData(SensorBuilder builder) {
        this.robot = builder.robot;
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
        this.leftObstacle = builder.leftObstacle;
        this.frontObstacle = builder.frontObstacle;
        this.rightObstacle = builder.rightObstacle;
        this.batteryLevel = builder.batteryLevel;
    }

    public static class SensorBuilder {
        private Robot robot;
        private Integer positionX = 0;
        private Integer positionY = 0;
        private Boolean leftObstacle = false;
        private Boolean frontObstacle = false;
        private Boolean rightObstacle = false;
        private Integer batteryLevel = 100;

        public SensorBuilder robot(Robot robot) {
            this.robot = robot;
            return this;
        }

        public SensorBuilder position(Integer x, Integer y) {
            if (x != null) this.positionX = x;
            if (y != null) this.positionY = y;
            return this;
        }

        public SensorBuilder obstacles(Boolean left, Boolean front, Boolean right) {
            if (left != null) this.leftObstacle = left;
            if (front != null) this.frontObstacle = front;
            if (right != null) this.rightObstacle = right;
            return this;
        }

        public SensorBuilder batteryLevel(Integer batteryLevel) {
            if (batteryLevel != null) this.batteryLevel = batteryLevel;
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