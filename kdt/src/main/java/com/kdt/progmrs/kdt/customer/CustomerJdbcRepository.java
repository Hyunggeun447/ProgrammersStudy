package com.kdt.progmrs.kdt.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
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

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {

        int update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(?), ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()));
        if (update != 1) {
            throw new RuntimeException("Nothing wa inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {

        int update = jdbcTemplate.update("update customers set name = ?, email = ?, last_login_at = ? where customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
                customer.getCustomerId().toString().getBytes());
        if (update != 1) {
            throw new RuntimeException("Nothing wa inserted");
        }
        return customer;
    }


    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper());
    }

    private static final RowMapper<Customer> customerRowMapper() {
        return (resultSet, i) -> {
            String customerName = resultSet.getString("name");
            UUID customerId = toUUID(resultSet.getBytes("customer_id"));
            String email = resultSet.getString("email");
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                    resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
        };
    }

    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        String customerName = resultSet.getString("name");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        allCustomers.add(new Customer(customerId, customerName, email, lastLoginAt, createdAt));
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("select * from customers where customer_id  = UUId_TO_BIN(?)", customerRowMapper(), customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("select * from customers where name  = ?", customerRowMapper(), name));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("select * from customers where email = ?", customerRowMapper(), email));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers");
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }
}
