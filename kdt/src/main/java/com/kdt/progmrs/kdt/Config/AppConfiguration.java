package com.kdt.progmrs.kdt.Config;

import com.kdt.progmrs.kdt.order.Order;
import com.kdt.progmrs.kdt.voucher.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@ComponentScan(basePackages = {"com.kdt.progmrs.kdt.order","com.kdt.progmrs.kdt.voucher"})
@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
//@PropertySource("application.properties")
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
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
