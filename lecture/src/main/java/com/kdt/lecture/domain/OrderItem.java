package com.kdt.lecture.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int quantity;

    @Column(name = "order_id")
    private String order_id;
    @Column(name = "item_id")
    private Long item_id;
}
