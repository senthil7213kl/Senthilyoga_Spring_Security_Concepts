package com.sen.springSecuirtySection1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public String sayWelocme() {
        return "Welcome to Spring application with out Security";
    }
}
