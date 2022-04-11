package com.kdt.progmrs.kdt.customer;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Customer {

    private final UUID customerId;

    private String name;

    private final String email;

    private LocalDateTime lastLoginAt;

    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("name should not be blank");
        }
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

}
