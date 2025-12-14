/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.controller;

import com.robotdelivery.model.Delivery;
import com.robotdelivery.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin(origins = "*")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(deliveryService.createDelivery(request));
    }

    @PostMapping("/{deliveryId}/start")
    public ResponseEntity<String> startDelivery(@PathVariable Long deliveryId) {
        deliveryService.startDelivery(deliveryId);
        return ResponseEntity.ok("Delivery started");
    }

    @PostMapping("/{deliveryId}/complete")
    public ResponseEntity<String> completeDelivery(@PathVariable Long deliveryId) {
        deliveryService.completeDelivery(deliveryId);
        return ResponseEntity.ok("Delivery completed");
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStats() {
        long completed = deliveryService.getCompletedCount();
        long failed = deliveryService.getFailedCount();
        return ResponseEntity.ok("Completed: " + completed + ", Failed: " + failed);
    }
}