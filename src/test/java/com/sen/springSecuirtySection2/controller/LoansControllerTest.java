package com.sen.springSecuirtySection2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoansController.class)
class LoansControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getLoanDetails() throws Exception {
        mockMvc.perform(get("/loans")).andExpect(status().isOk())
                .andExpect(content().string("Loan details"));
    }
}