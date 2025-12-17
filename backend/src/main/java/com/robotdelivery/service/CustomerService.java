/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.service;

import com.robotdelivery.factory.RestaurantFactory;
import com.robotdelivery.model.Customer;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repositorys.CustomerRepository;
import com.robotdelivery.util.PasswordUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestaurantFactory restaurantFactory;
    private final PasswordUtil passwordUtil;
    private final EventPublisher eventPublisher;

    public CustomerService(CustomerRepository customerRepository,
                           RestaurantFactory restaurantFactory,
                           PasswordUtil passwordUtil,
                           EventPublisher eventPublisher) {
        this.customerRepository = customerRepository;
        this.restaurantFactory = restaurantFactory;
        this.passwordUtil = passwordUtil;
        this.eventPublisher = eventPublisher;
    }

    public Customer registerCustomer(Map<String, String> request) {
        String email = request.get("email");

        if (customerRepository.existsByEmail(email)) {
            eventPublisher.notifyError("Email already registered: " + email);
            throw new RuntimeException("Email already exists");
        }

        Customer customer = restaurantFactory.createCustomer(request);
        customer = customerRepository.save(customer);

        eventPublisher.notifyCustomerRegistered(email, customer.getFirstName());

        return customer;
    }


    public Customer loginCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordUtil.verifyPassword(password, customer.getPasswordHash())) {
            eventPublisher.notifyError("Failed login attempt: " + email);
            throw new RuntimeException("Invalid email or password");
        }

        customer.setLastLogin(LocalDateTime.now());
        customerRepository.save(customer);

        eventPublisher.notifyCustomerLogin(email);

        return customer;
    }


    public Customer updateProfile(Long customerId, Map<String, String> updates) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (updates.containsKey("firstName")) {
            customer.setFirstName(updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            customer.setLastName(updates.get("lastName"));
        }
        if (updates.containsKey("phoneNumber")) {
            customer.setPhoneNumber(updates.get("phoneNumber"));
        }
        if (updates.containsKey("address")) {
            customer.setAddress(updates.get("address"));
        }
        if (updates.containsKey("city")) {
            customer.setCity(updates.get("city"));
        }
        if (updates.containsKey("postalCode")) {
            customer.setPostalCode(updates.get("postalCode"));
        }

        customer = customerRepository.save(customer);

        eventPublisher.notifyProfileUpdated(customer.getEmail());

        return customer;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}