package com.kdt.progmrs.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.assertj.core.api.filter.NotFilter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringJUnitConfig
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {


    @Configuration
    @ComponentScan(basePackages = {"com.kdt.progmrs.kdt"})
    static class config {

        @Bean
        public DataSource dataSource() {

//            return new EmbeddedDatabaseBuilder()
//                    .generateUniqueName(true)
//                    .setType(H2)
//                    .setScriptEncoding("UTF-8")
//                    .ignoreFailedDrops(true)
//                    .addScript("schema.sql")
//                    .build();
            HikariDataSource dataSource = DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost/order_mgmt")
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
//                    .username("root")
                    .username("test")
                    .password("1234!")
                    .type(HikariDataSource.class)
                    .build();



            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new CustomerNamedJdbcRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerServiceImpl(customerRepository);
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;




    @Autowired
    DataSource dataSource;

    private static EmbeddedMysql embeddedMysql;


    @BeforeAll
    static void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();

    }


    @AfterAll
    static void end() {
        embeddedMysql.stop();
    }

    Customer customer;

//    @BeforeEach
//    void cleanUp() {
//        customerRepository.deleteAll();
//        customer = new Customer(UUID.randomUUID(),
//                "test-user",
//                "test11-user@naver.com",
//                LocalDateTime.now());
//
//        try {
//            customerRepository.insert(customer);
////            customerRepository.insert(customer);
//        } catch (DuplicateKeyException e) {
//            log.error("Got BadSql -> {}", e.getMessage());
//        }
//    }

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("멀티 인서트")
    public void multiInsertTest() throws Exception {
        Customer customer1 = new Customer(UUID.randomUUID(), "a", "a@.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "b", "b@.com", LocalDateTime.now());

        List<Customer> customers = new ArrayList<>();

        customers.add(customer1);
        customers.add(customer2);

        customerService.createCustomers(customers);

        List<Customer> allCustomers = customerRepository.findAll();
//        assertThat(allCustomers.size()).isEqualTo(2);
        assertThat(allCustomers, containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1))));
    }

    @Test
    @DisplayName("멀티 인서트 실패 -> 트랜잭션 롤백")
    public void multiInsertRollbackTest() throws Exception {
        Customer customer1 = new Customer(UUID.randomUUID(), "c", "c@.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "d", "c@.com", LocalDateTime.now());

        List<Customer> customers = new ArrayList<>();

        customers.add(customer1);
        customers.add(customer2);

        try {
            customerService.createCustomers(customers);
        } catch (DataAccessException e) {

        }

        List<Customer> allCustomers = customerRepository.findAll();
        assertThat(allCustomers.size()).isEqualTo(0);
//        assertThat(allCustomers, containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1))));
    }


}