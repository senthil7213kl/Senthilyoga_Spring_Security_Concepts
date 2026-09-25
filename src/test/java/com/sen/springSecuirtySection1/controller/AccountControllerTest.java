package com.sen.springSecuirtySection1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestGetUserAccount() throws Exception {
        mockMvc.perform(get("/myAccount") .with(httpBasic("jobportal", "user@7213"))).andExpect(status().isOk())
                .andExpect(content().string("Account details"));
    }

    @Test
    public void TestGetAdminAccount() throws Exception {
        mockMvc.perform(get("/myAccount") .with(httpBasic("jobportaladmin", "admin@7213"))).andExpect(status().isOk())
                .andExpect(content().string("Account details"));
    }
}
