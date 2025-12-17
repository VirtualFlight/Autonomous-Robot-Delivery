/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.controller;

import com.robotdelivery.model.Customer;
import com.robotdelivery.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Map<String, String> request) {
        Customer customer = customerService.registerCustomer(request);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        Customer customer = customerService.loginCustomer(email, password);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{customerId}/profile")
    public ResponseEntity<Customer> updateProfile(
            @PathVariable Long customerId,
            @RequestBody Map<String, String> updates) {
        Customer customer = customerService.updateProfile(customerId, updates);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }
}