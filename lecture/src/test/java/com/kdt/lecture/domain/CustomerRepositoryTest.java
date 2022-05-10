package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void Test() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyungguen");
        customer.setLastName("Park");

        //when
        repository.save(customer);

        //then
        Customer savedCustomer = repository.findById(1L).get();
        log.info("{} {}", savedCustomer.getFirstName(), savedCustomer.getLastName());

    }

}