/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "robot_id")
    private Robot robot;

    private Integer startX;
    private Integer startY;
    private Integer destinationX;
    private Integer destinationY;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private Integer distanceTraveled;
    private Integer obstaclesEncountered;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Delivery() {}

    private Delivery(Builder builder) {
        this.robot = builder.robot;
        this.startX = builder.startX;
        this.startY = builder.startY;
        this.destinationX = builder.destinationX;
        this.destinationY = builder.destinationY;
        this.status = builder.status;
        this.distanceTraveled = builder.distanceTraveled;
        this.obstaclesEncountered = builder.obstaclesEncountered;
    }

    public static class Builder {
        private Robot robot;
        private Integer startX;
        private Integer startY;
        private Integer destinationX;
        private Integer destinationY;
        private DeliveryStatus status = DeliveryStatus.PENDING;
        private Integer distanceTraveled = 0;
        private Integer obstaclesEncountered = 0;

        public Builder robot(Robot robot) {
            this.robot = robot;
            return this;
        }

        public Builder start(Integer x, Integer y) {
            this.startX = x;
            this.startY = y;
            return this;
        }

        public Builder destination(Integer x, Integer y) {
            this.destinationX = x;
            this.destinationY = y;
            return this;
        }

        public Builder status(DeliveryStatus status) {
            this.status = status;
            return this;
        }

        public Delivery build() {
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