package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.kdt.lecture.domain.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    EntityManagerFactory emf;

    String uuid = UUID.randomUUID().toString();


    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 연관관계_테스트() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member = new Member();
        member.setName("name");
        member.setNickName("nick");
        member.setAddress("주소");
        member.setAge(33);

        em.persist(member);


        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("qnwotl");
        order.setMember(member);
//        member.setOrders(Lists.newArrayList(order));

        em.persist(order);

        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());
        log.info("{}", entity.getMember().getNickName());
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());

    }

    @Test
    void JPA_query() {
        Order order = new Order();
        order.setUuid(uuid);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);


        orderRepository.save(order);

        List<Order> all = orderRepository.findAll();
        orderRepository.existsById(uuid);
    }

    @Test
    @Transactional
    void METHOD_QUERY() {
        orderRepository.findAllByOrderStatus(OrderStatus.OPENED);
        orderRepository.findAllByOrderStatusOrderByOrderDatetime(OrderStatus.OPENED);
    }

    @Test
    void NAMED_QUERY() {
        orderRepository.findAllByOrderStatus(OrderStatus.OPENED);
        orderRepository.findAllByOrderStatusOrderByOrderDatetime(OrderStatus.OPENED);
    }

}