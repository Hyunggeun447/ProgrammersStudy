package org.programmers;


import junit.framework.Assert;

import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var customerId = UUID.randomUUID();
        var orderItems = new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};
        var order = new Order(UUID.randomUUID(), customerId, orderItems, 10L);

        Assert.isTrue(order.totalAmount() -= = 90L, "totalAmount " + order.totalAmount())

    }
}
