package com.kdt.progmrs.kdt.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
//    private final TransactionTemplate transactionTemplate;

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
//        this.transactionTemplate = transactionTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer insert(Customer customer) {

        int update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(:customerId), :name, :email, :createdAt)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing wa inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {

        int update = jdbcTemplate.update("update customers set name = :name, email = :email, last_login_at = :lastLoginAt where customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));
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

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id  = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId",customerId.toString().getBytes()),
                    customerRowMapper()));

        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where name  = :name",
                    Collections.singletonMap("name", name),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where email  = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.emptyMap());
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.emptyMap(),Integer.class);
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }

//    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
//        String customerName = resultSet.getString("name");
//        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
//        String email = resultSet.getString("email");
//        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
//        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
//                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
//        allCustomers.add(new Customer(customerId, customerName, email, lastLoginAt, createdAt));
//    }


//    public void testTransaction(Customer customer) {
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus status) {
//                jdbcTemplate.update("update customers set name = :name where customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
//                jdbcTemplate.update("update customers set email = :email where customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
//            }
//        });
//    }
}
