/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.repositorys;

import com.robotdelivery.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);
    List<Order> findByStatus(Order.OrderStatus status);
    Long countByStatus(Order.OrderStatus status);
}