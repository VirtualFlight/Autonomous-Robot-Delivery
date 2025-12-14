/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.repository;

import com.robotdelivery.model.Delivery;
import com.robotdelivery.model.Delivery.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByStatus(DeliveryStatus status);
    Long countByStatus(DeliveryStatus status);
}