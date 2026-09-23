package com.sen.springSecuirtySection1;

import com.sen.springSecuirtySection1.controller.WelcomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EasyBankBackEndApplicationTests {

    @Autowired
    private WelcomeController welcomeController;

    @Test
    void contextLoads() {
        assertThat(welcomeController).isNotNull();
    }

}
