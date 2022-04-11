package com.kdt.progmrs.kdt.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class JdbcCustomerRepository {
    private final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) values (UUID_TO_BIN(?), ?, ?)";
    private final String UPDATE_BY_ID_SQL = "update customers set name = ? where customer_id = UUID_TO_BIN(?)";



    public List<String> findNames(String name) {

        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
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

    public List<String> findAll() {
        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
//                log.info("customerId -> {}, name -> {}, craetedAt -> {}", customerId, customerName, createdAt);
                names.add(customerName);
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return names;
    }

    public int insertCustomer(UUID customerId, String name, String email) {

        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return 0;
    }

    public int updateCustomerName(UUID customerId, String name) {

        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return 0;
    }

    public int deleteAllCustomer() {

        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return 0;
    }

    public List<UUID> findAllIds() {
        List<UUID> uuids = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_Id"));
                UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
                uuids.add(customerId);
            }
        } catch (SQLException e) {
            log.error("Got error while closing connection", e);
        }
        return uuids;
    }

    public static void main(String[] args) {

        JdbcCustomerRepository customerRepository = new JdbcCustomerRepository();

        customerRepository.deleteAllCustomer();

        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);
        UUID uuid2 = UUID.randomUUID();
        System.out.println("uuid2 = " + uuid2);
        UUID uuid3 = UUID.randomUUID();
        System.out.println("uuid3 = " + uuid3);
        customerRepository.insertCustomer(uuid, "newMember1", "newEmail1@asd.com");
        customerRepository.insertCustomer(uuid2, "newMember2", "newEmail2@asd.com");
        customerRepository.insertCustomer(uuid3, "newMember3", "newEmail3@asd.com");

        System.out.println("--------------------------------------");
        List<UUID> allIds = customerRepository.findAllIds();
        allIds.forEach(System.out::println);
//        List<String> all = customerRepository.findAll();
//        all.forEach(System.out::println);
//
//
//        customerRepository.updateCustomerName(uuid, "updateName1");
//        customerRepository.updateCustomerName(uuid2, "updateName3");
//        customerRepository.updateCustomerName(uuid3, "updateName5");
//
//        List<String> all1 = customerRepository.findAll();
//        all1.forEach(System.out::println);


    }
}
