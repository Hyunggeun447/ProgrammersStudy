package com.kdt.progmrs.kdt.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.UUID;

@Slf4j
public class JdbcCustomerRepository {

    public static void main(String[] args) {

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from  customers");
        ) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                log.info("customerId -> {}, name -> {}", customerId, name);
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }

    }
}
