/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.config;

import com.robotdelivery.observer.ConsoleLogger;
import com.robotdelivery.observer.EventPublisher;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class ObserverConfig {

    private final EventPublisher eventPublisher;
    private final ConsoleLogger consoleLogger;

    public ObserverConfig(EventPublisher eventPublisher, ConsoleLogger consoleLogger) {
        this.eventPublisher = eventPublisher;
        this.consoleLogger = consoleLogger;
    }

    @PostConstruct
    public void setupObservers() {
        eventPublisher.addObserver(consoleLogger);
        System.out.println("CONFIRMED: Console Logger registered as observer");
    }
}