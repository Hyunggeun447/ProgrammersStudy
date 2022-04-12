package com.kdt.progmrs.kdt.customer;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
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

    @BeforeEach
    void cleanUp() {
        customerRepository.deleteAll();
        Customer customer = new Customer(UUID.randomUUID(), "test-user", "test11-user@naver.com", LocalDateTime.now());

        customerRepository.insert(customer);
    }

    @Test
    public void testHikariConnectionPool() throws Exception {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @DisplayName("전체 고객 조회")
    public void testFindAll() throws Exception {

        List<Customer> all = customerRepository.findAll();
        assertThat(all.isEmpty()).isFalse();

//        Thread.sleep(10000);
    }

    @Test
    public void findByName() throws Exception {

        Optional<Customer> newMember1 = customerRepository.findByName("test-user");

        assertThat(newMember1.isPresent()).isTrue();

    }
    @Test
    public void findByEmail() throws Exception {

        Optional<Customer> byEmail = customerRepository.findByEmail("test11-user@naver.com");

        assertThat(byEmail.isPresent()).isTrue();

    }
    @Test
    public void findById() throws Exception {

        Customer customer2 = new Customer(UUID.randomUUID(), "test-user2", "test121-user@naver.com", LocalDateTime.now());
        customerRepository.insert(customer2);

        Optional<Customer> byId = customerRepository.findById(customer2.getCustomerId());

        assertThat(byId.isPresent()).isTrue();

    }

    @Test
    public void insertTest() throws Exception {
        Customer customer1 = new Customer(UUID.randomUUID(), "test-user1", "test111-user@naver.com", LocalDateTime.now());
        customerRepository.insert(customer1);

        UUID customerId = customer1.getCustomerId();
        System.out.println("customerId = " + customerId);
        Optional<Customer> byId = customerRepository.findById(customerId);
        UUID customerId2 = byId.get().getCustomerId();

        assertThat(byId.get().getCustomerId()).isEqualTo(customer1.getCustomerId());
    }

    @Test
    public void deleteTest() throws Exception {
        customerRepository.deleteAll();
    }

    @Test
    public void updateTest() {
        Customer customer = new Customer(UUID.randomUUID(), "test-user123", "test111111-user@naver.com", LocalDateTime.now());
        customerRepository.insert(customer);
        customer.changeName("newNameNew");

        customerRepository.update(customer);

        Optional<Customer> newNameNew = customerRepository.findByName("newNameNew");
        assertThat(newNameNew.isPresent()).isTrue();


    }
}