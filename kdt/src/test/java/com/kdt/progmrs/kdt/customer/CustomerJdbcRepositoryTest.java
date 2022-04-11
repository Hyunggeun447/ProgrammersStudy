package com.kdt.progmrs.kdt.customer;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@Slf4j
class CustomerJdbcRepositoryTest {

    @Autowired
    CustomerJdbcRepository customerRepository;

    @Configuration
    @ComponentScan(basePackages = {"com.kdt.progmrs.kdt"})
    static class config {


        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }
    }

    @Autowired
    DataSource dataSource;

    @Test
    public void testHikariConnectionPool() throws Exception {

        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");

    }

    @Test
    @DisplayName("전체 고객 조회")
    public void testFindAll() throws Exception {

        List<Customer> all = customerRepository.findAll();
        assertThat(all.isEmpty()).isFalse();

        Thread.sleep(10000);
    }
}