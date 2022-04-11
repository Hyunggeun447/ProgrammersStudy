package com.kdt.progmrs.kdt;

import com.kdt.progmrs.kdt.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(
            basePackages = {"com.kdt.progmrs.kdt"}
    )
    static class Config {

    }
    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("applicationContext가 생성되어야한다")
    public void testApplicationContext() throws Exception {
        assertThat(context).isNotNull();
    }
}
