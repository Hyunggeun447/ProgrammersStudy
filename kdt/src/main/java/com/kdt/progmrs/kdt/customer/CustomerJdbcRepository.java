package com.kdt.progmrs.kdt.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final DataSource dataSource;

    public CustomerJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer insert(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }


    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("select * from customers");
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                        resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

                String email = resultSet.getString("email");
                log.info("customerId -> {}, name -> {}, email -> {}, createdAt -> {}, lastLoginAt -> {}", customerId, customerName, email, createdAt, lastLoginAt);

                customers.add(new Customer(customerId, customerName, email, createdAt, lastLoginAt));
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
            throw new RuntimeException();
        }
        return customers;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }
}
