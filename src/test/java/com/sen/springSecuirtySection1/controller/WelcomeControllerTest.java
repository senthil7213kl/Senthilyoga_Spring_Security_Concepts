package com.sen.springSecuirtySection1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WelcomeController.class)
class WelcomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void TestSayWelcome() throws Exception {
        mockMvc.perform(get("/welcome")).
                andExpect(status().isOk()).
                andExpect(content().string("Welcome to Spring application with out Security"));
    }
}