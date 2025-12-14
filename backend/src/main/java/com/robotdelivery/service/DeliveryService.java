/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.service;

import com.robotdelivery.factory.EntityFactory;
import com.robotdelivery.model.Delivery;
import com.robotdelivery.model.Delivery.DeliveryStatus;
import com.robotdelivery.model.Robot;
import com.robotdelivery.model.Robot.RobotStatus;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repository.DeliveryRepository;
import com.robotdelivery.repository.RobotRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final RobotRepository robotRepository;
    private final EntityFactory entityFactory;
    private final EventPublisher eventPublisher;

    public DeliveryService(DeliveryRepository deliveryRepository,
                           RobotRepository robotRepository,
                           EntityFactory entityFactory,
                           EventPublisher eventPublisher) {
        this.deliveryRepository = deliveryRepository;
        this.robotRepository = robotRepository;
        this.entityFactory = entityFactory;
        this.eventPublisher = eventPublisher;
    }

    public Delivery createDelivery(Map<String, Object> request) {
        String robotId = (String) request.get("robotId");
        Integer destX = (Integer) request.get("destinationX");
        Integer destY = (Integer) request.get("destinationY");

        Robot robot = robotRepository.findByRobotId(robotId)
                .orElseThrow(() -> new RuntimeException("Robot not found"));

        if (robot.getStatus() != RobotStatus.IDLE) {
            eventPublisher.notifyError("Robot " + robotId + " is busy");
            throw new RuntimeException("Robot is busy");
        }

        Delivery delivery = entityFactory.createDelivery(robot, destX, destY);
        delivery = deliveryRepository.save(delivery);

        robot.setStatus(RobotStatus.DELIVERING);
        robotRepository.save(robot);

        eventPublisher.notifyDeliveryCreated(delivery.getId(), robotId);

        return delivery;
    }

    public void startDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        delivery.setStatus(DeliveryStatus.IN_PROGRESS);
        delivery.setStartTime(LocalDateTime.now());
        deliveryRepository.save(delivery);
    }

    public void completeDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        delivery.setStatus(DeliveryStatus.COMPLETED);
        delivery.setEndTime(LocalDateTime.now());
        deliveryRepository.save(delivery);

        Robot robot = delivery.getRobot();
        robot.setStatus(RobotStatus.IDLE);
        robotRepository.save(robot);

        eventPublisher.notifyDeliveryCompleted(deliveryId);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public long getCompletedCount() {
        return deliveryRepository.countByStatus(DeliveryStatus.COMPLETED);
    }

    public long getFailedCount() {
        return deliveryRepository.countByStatus(DeliveryStatus.FAILED);
    }
}