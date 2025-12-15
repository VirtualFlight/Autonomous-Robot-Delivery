package com.robotdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "robot_id", nullable = false)
    private Robot robot;

    @Column(name = "start_x", nullable = false)
    private Integer startX;

    @Column(name = "start_y", nullable = false)
    private Integer startY;

    @Column(name = "destination_x", nullable = false)
    private Integer destinationX;

    @Column(name = "destination_y", nullable = false)
    private Integer destinationY;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private Integer distanceTraveled;
    private Integer obstaclesEncountered;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Delivery() {}

    private Delivery(DeliveryBuilder builder) {
        this.robot = builder.robot;
        this.startX = builder.startX;
        this.startY = builder.startY;
        this.destinationX = builder.destinationX;
        this.destinationY = builder.destinationY;
        this.status = builder.status;
        this.distanceTraveled = builder.distanceTraveled;
        this.obstaclesEncountered = builder.obstaclesEncountered;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    public static class DeliveryBuilder {
        private Robot robot;
        private Integer startX = 0;
        private Integer startY = 0;
        private Integer destinationX = 0;
        private Integer destinationY = 0;
        private DeliveryStatus status = DeliveryStatus.PENDING;
        private Integer distanceTraveled = 0;
        private Integer obstaclesEncountered = 0;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public DeliveryBuilder robot(Robot robot) {
            this.robot = robot;
            return this;
        }

        public DeliveryBuilder start(Integer x, Integer y) {
            this.startX = x;
            this.startY = y;
            return this;
        }

        public DeliveryBuilder destination(Integer x, Integer y) {
            this.destinationX = x;
            this.destinationY = y;
            return this;
        }

        public DeliveryBuilder status(DeliveryStatus status) {
            this.status = status;
            return this;
        }

        public DeliveryBuilder distanceTraveled(Integer distanceTraveled) {
            this.distanceTraveled = distanceTraveled;
            return this;
        }

        public DeliveryBuilder obstaclesEncountered(Integer obstacles) {
            this.obstaclesEncountered = obstacles;
            return this;
        }

        public DeliveryBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public DeliveryBuilder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Delivery build() {
            if (startX == null) startX = 0;
            if (startY == null) startY = 0;
            if (destinationX == null) destinationX = 0;
            if (destinationY == null) destinationY = 0;
            if (distanceTraveled == null) distanceTraveled = 0;
            if (obstaclesEncountered == null) obstaclesEncountered = 0;
            if (status == null) status = DeliveryStatus.PENDING;
            return new Delivery(this);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Robot getRobot() { return robot; }
    public void setRobot(Robot robot) { this.robot = robot; }

    public Integer getStartX() { return startX; }
    public void setStartX(Integer startX) { this.startX = startX; }

    public Integer getStartY() { return startY; }
    public void setStartY(Integer startY) { this.startY = startY; }

    public Integer getDestinationX() { return destinationX; }
    public void setDestinationX(Integer destinationX) { this.destinationX = destinationX; }

    public Integer getDestinationY() { return destinationY; }
    public void setDestinationY(Integer destinationY) { this.destinationY = destinationY; }

    public DeliveryStatus getStatus() { return status; }
    public void setStatus(DeliveryStatus status) { this.status = status; }

    public Integer getDistanceTraveled() { return distanceTraveled; }
    public void setDistanceTraveled(Integer distanceTraveled) { this.distanceTraveled = distanceTraveled; }

    public Integer getObstaclesEncountered() { return obstaclesEncountered; }
    public void setObstaclesEncountered(Integer obstaclesEncountered) { this.obstaclesEncountered = obstaclesEncountered; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public enum DeliveryStatus {
        PENDING, IN_PROGRESS, COMPLETED, FAILED
    }
}