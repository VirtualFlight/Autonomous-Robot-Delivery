/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.repository;

import com.robotdelivery.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Long> {
    Optional<Robot> findByRobotId(String robotId);
}