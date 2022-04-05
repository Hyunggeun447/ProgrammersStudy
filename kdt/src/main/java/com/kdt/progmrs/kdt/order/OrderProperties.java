package com.kdt.progmrs.kdt.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProperties implements InitializingBean {

    @Value("v1.1.1")
    private String version;

    @Value("2000000000")
    private int minimumOrderAmount;

    @Value("12,12,12,12,12,12")
    private List<String> supportVendors;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("version = " + version);
        System.out.println("minimumOrderAmount = " + minimumOrderAmount);
        System.out.println("supportVendors = " + supportVendors);
    }
}
