package com.sen.springSecuirtySection1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String pwd;
    private String role;
}
//sample json request for registration:
// {
//     "email": "john.doe@example.com",
//     "pwd": "password123",
//     "role": "USER"
// }