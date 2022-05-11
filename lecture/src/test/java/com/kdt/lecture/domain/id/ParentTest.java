package com.kdt.lecture.domain.id;

import com.kdt.lecture.domain.id.Parent;
import com.kdt.lecture.domain.id.ParentId;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

//@SpringBootTest
//@Slf4j
//@WebAppConfiguration
//class ParentTest {
//
//    @Autowired
//    EntityManagerFactory emf;
//
//    @Test
//    void multi_key_test() {
//        EntityManager entityManager = emf.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        transaction.begin();
//
//        Parent parent = new Parent();
//        parent.setId1("id1");
//        parent.setId2("id2");
//        entityManager.persist(parent);
//
//        transaction.commit();
//
//        Parent entity = entityManager.find(Parent.class, new ParentId("id1", "id2"));
//        log.info("{}, {}", entity.getId1(), entity.getId2());
//    }
//}