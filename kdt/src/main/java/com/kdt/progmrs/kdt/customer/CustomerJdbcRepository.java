package com.kdt.progmrs.kdt.customer;

import lombok.extern.slf4j.Slf4j;
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

    public CustomerJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer insert(Customer customer) {
        String INSERT_SQL = "insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(?), ?, ?, ?)";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2,customer.getName());
            statement.setString(3,customer.getEmail());
            statement.setTimestamp(4,Timestamp.valueOf(customer.getCreatedAt()));
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
            return customer;
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
            throw new RuntimeException();
        }
    }

    @Override
    public Customer update(Customer customer) {
        String UPDATE_BY_ID_SQL = "update customers set name = ?, email = ?, last_login_at = ? where customer_id = UUID_TO_BIN(?)";

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setTimestamp(3, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            statement.setBytes(4, customer.getCustomerId().toString().getBytes());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate != 1) {
                throw new RuntimeException("Nothing was update");
            }
            return customer;
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }
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
                mapToCustomer(customers, resultSet);
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
            throw new RuntimeException();
        }
        return customers;
    }

    private void mapToCustomer(List<Customer> customers, ResultSet resultSet) throws SQLException {
        String customerName = resultSet.getString("name");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        customers.add(new Customer(customerId, customerName, email, createdAt, lastLoginAt));
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        List<Customer> allCustomers = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement("select * from customers where customer_id  = UUId_TO_BIN(?)");
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return allCustomers.stream().findFirst();

    }

    @Override
    public Optional<Customer> findByName(String name) {
        List<Customer> allCustomers = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement("select * from customers where name  = ?");
        ) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        List<Customer> allCustomers = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement("select * from customers where email  = ?");
        ) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public void deleteAll() {
        String DELETE_ALL_SQL = "delete from customers";

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }

    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }
}
