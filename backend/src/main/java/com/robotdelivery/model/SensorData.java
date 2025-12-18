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

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;

    @Column(nullable = false)
    private Boolean leftObstacle;

    @Column(nullable = false)
    private Boolean frontObstacle;

    @Column(nullable = false)
    private Boolean rightObstacle;

    @Column(name = "distance_left")
    private Double distanceLeft;

    @Column(name = "distance_middle")
    private Double distanceMiddle;

    @Column(name = "distance_right")
    private Double distanceRight;

    @Column(name = "accel_x")
    private Double accelX;

    @Column(name = "accel_y")
    private Double accelY;

    @Column(name = "accel_z")
    private Double accelZ;

    @Column(name = "gyro_x")
    private Double gyroX;

    @Column(name = "gyro_y")
    private Double gyroY;

    @Column(name = "gyro_z")
    private Double gyroZ;

    @Column
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
        this.distanceLeft = builder.distanceLeft;
        this.distanceMiddle = builder.distanceMiddle;
        this.distanceRight = builder.distanceRight;
        this.accelX = builder.accelX;
        this.accelY = builder.accelY;
        this.accelZ = builder.accelZ;
        this.gyroX = builder.gyroX;
        this.gyroY = builder.gyroY;
        this.gyroZ = builder.gyroZ;
        this.batteryLevel = builder.batteryLevel;
    }

    public static class SensorBuilder {
        private Robot robot;
        private Integer positionX;
        private Integer positionY;
        private Boolean leftObstacle = false;
        private Boolean frontObstacle = false;
        private Boolean rightObstacle = false;
        private Double distanceLeft;
        private Double distanceMiddle;
        private Double distanceRight;
        private Double accelX;
        private Double accelY;
        private Double accelZ;
        private Double gyroX;
        private Double gyroY;
        private Double gyroZ;
        private Integer batteryLevel;

        public SensorBuilder robot(Robot robot) {
            this.robot = robot;
            return this;
        }

        public SensorBuilder position(Integer x, Integer y) {
            this.positionX = x;
            this.positionY = y;
            return this;
        }

        public SensorBuilder obstacles(Boolean left, Boolean front, Boolean right) {
            if (left != null) this.leftObstacle = left;
            if (front != null) this.frontObstacle = front;
            if (right != null) this.rightObstacle = right;
            return this;
        }

        public SensorBuilder distances(Double left, Double middle, Double right) {
            this.distanceLeft = left;
            this.distanceMiddle = middle;
            this.distanceRight = right;
            return this;
        }

        public SensorBuilder accelerometer(Double x, Double y, Double z) {
            this.accelX = x;
            this.accelY = y;
            this.accelZ = z;
            return this;
        }

        public SensorBuilder gyroscope(Double x, Double y, Double z) {
            this.gyroX = x;
            this.gyroY = y;
            this.gyroZ = z;
            return this;
        }

        public SensorBuilder batteryLevel(Integer batteryLevel) {
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

    public Double getDistanceLeft() { return distanceLeft; }
    public void setDistanceLeft(Double distanceLeft) { this.distanceLeft = distanceLeft; }

    public Double getDistanceMiddle() { return distanceMiddle; }
    public void setDistanceMiddle(Double distanceMiddle) { this.distanceMiddle = distanceMiddle; }

    public Double getDistanceRight() { return distanceRight; }
    public void setDistanceRight(Double distanceRight) { this.distanceRight = distanceRight; }

    public Double getAccelX() { return accelX; }
    public void setAccelX(Double accelX) { this.accelX = accelX; }

    public Double getAccelY() { return accelY; }
    public void setAccelY(Double accelY) { this.accelY = accelY; }

    public Double getAccelZ() { return accelZ; }
    public void setAccelZ(Double accelZ) { this.accelZ = accelZ; }

    public Double getGyroX() { return gyroX; }
    public void setGyroX(Double gyroX) { this.gyroX = gyroX; }

    public Double getGyroY() { return gyroY; }
    public void setGyroY(Double gyroY) { this.gyroY = gyroY; }

    public Double getGyroZ() { return gyroZ; }
    public void setGyroZ(Double gyroZ) { this.gyroZ = gyroZ; }

    public Integer getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(Integer batteryLevel) { this.batteryLevel = batteryLevel; }

    public LocalDateTime getTimestamp() { return timestamp; }
}