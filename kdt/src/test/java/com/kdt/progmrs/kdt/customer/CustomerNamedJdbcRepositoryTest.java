package com.kdt.progmrs.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerNamedJdbcRepositoryTest {


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
    }

    @Autowired
    CustomerNamedJdbcRepository customerRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;


    @BeforeAll
    void setup() {
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
    void end() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void cleanUp() {
        customerRepository.deleteAll();
        Customer customer = new Customer(UUID.randomUUID(),
                "test-user",
                "test11-user@naver.com",
                LocalDateTime.now());

        try {
            customerRepository.insert(customer);
//            customerRepository.insert(customer);
        } catch (DuplicateKeyException e) {
            log.error("Got BadSql -> {}", e.getMessage());
        }
    }

    @Test
//    @Disabled
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

    @Test
    public void countTest() throws Exception {

        int i = customerRepository.countAll();
        assertThat(i).isEqualTo(1);
    }
}