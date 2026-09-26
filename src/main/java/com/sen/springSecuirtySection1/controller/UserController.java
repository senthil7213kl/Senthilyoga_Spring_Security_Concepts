package com.sen.springSecuirtySection1.controller;

import com.sen.springSecuirtySection1.model.Customer;
import com.sen.springSecuirtySection1.respository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class UserController {
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer != null) {
            return ResponseEntity.ok("User found with id: " + id + ", email: " + customer.getEmail() + ", role: " + customer.getRole());
        } else {
            return ResponseEntity.status(404).body("User not found with id: " + id);
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<Customer>> getAllUsers() {
        List<Customer> customers = (List<Customer>) customerRepo.findAll();
        return ResponseEntity.ok(customers);
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPwd());
        customer.setPwd(encodedPassword);
        Customer savedCustomer = customerRepo.save(customer);
        try {
            if (savedCustomer != null && savedCustomer.getId() > 0)
                return ResponseEntity.status(201).body("User registered successfully created with id: " + savedCustomer.getId());
            else {
                return ResponseEntity.status(400).body("User registration failed" +
                        " with id: " + customer.getId());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("exception occurred during registration failed with id: "+ e.getMessage());
        }
    }
}
