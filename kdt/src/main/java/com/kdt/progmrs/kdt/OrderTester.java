package com.kdt.progmrs.kdt;


import com.kdt.progmrs.kdt.order.OrderItem;
import com.kdt.progmrs.kdt.order.OrderService;
import com.kdt.progmrs.kdt.voucher.FixedAmountVoucher;
import com.kdt.progmrs.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);


        /**
         * Environment 활용하기
         * AppConfig에 @PropertySource("application.properties") 등록 후 끌어옮
         */
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String version = environment.getProperty("version");
        System.out.println("version = " + version);
        Integer property = environment.getProperty("mininum-order-count", Integer.class);
        System.out.println("property = " + property);
        List orderlistlist = environment.getProperty("orderlistlist", List.class);
        System.out.println("orderlistlist = " + orderlistlist);


        var customerId = UUID.randomUUID();
        
//        var voucherRepository = applicationContext.getBean(VoucherRepository.class);

        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        System.out.println(MessageFormat.format("voucherRepository = {0}", voucherRepository));
        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        System.out.println(MessageFormat.format("voucherRepository2 = {0}", voucherRepository2));

        System.out.println(MessageFormat.format("voucherRepository == voucherRepository2 -> {0}", voucherRepository == voucherRepository2));

        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = applicationContext.getBean(OrderService.class);

        var orderItems = new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        var order = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAMount {0} is not 90L", order.totalAmount()));


        applicationContext.close();

    }
}
