package com.kdt.progmrs.kdt.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class JdbcCustomerRepository {

    public List<String> findNames(String name) {

        String SELECT_SQL = "select * from customers WHERE name = ?";
        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(SELECT_SQL);
        ) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
//                log.info("customerId -> {}, name -> {}, craetedAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }

        return names;
    }

    public static void main(String[] args) {

        List<String> names = new JdbcCustomerRepository().findNames("tester01");
        names.forEach(System.out::println);

    }
}
