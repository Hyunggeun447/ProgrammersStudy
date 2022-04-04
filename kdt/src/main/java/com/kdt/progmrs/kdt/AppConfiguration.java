package com.kdt.progmrs.kdt;

import com.kdt.progmrs.kdt.order.Order;
import com.kdt.progmrs.kdt.voucher.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
//@ComponentScan(basePackages = {"com.kdt.progmrs.kdt.order","com.kdt.progmrs.kdt.voucher"})
@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
public class AppConfiguration {

    @Bean(initMethod = "init")
    public BeanOne beanOne() {
        return new BeanOne();
    }


}


class BeanOne implements InitializingBean {

    public void init() {
        System.out.println("[BeanOne] init called");

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("[BeanOne] afterPropertiesSet called!");

    }
}
