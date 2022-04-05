package com.kdt.progmrs.kdt.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {

    /**
     * ${}로 property 값을 가져올 수 있다.
     * 만약 값이 없을때 설정도 가능.
     */
//    @Value("v1.1.1")
//    @Value("${version}:v1.1.1.1.1.1")
    private String version;

//    @Value("2000000000")
    private int minimumOrderAmount;

//    @Value("12,12,12,12,12,12")
    private List<String> supportVendors;
    private String description;

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMinimumOrderAmount(int minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public void setSupportVendors(List<String> supportVendors) {
        this.supportVendors = supportVendors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public int getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public List<String> getSupportVendors() {
        return supportVendors;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("version3241423214 = " + version);
        System.out.println("minimumOrderAmount23412341124 = " + minimumOrderAmount);
        System.out.println("supportVendors124341234231 = " + supportVendors);
        System.out.println("description42342314213 = " + description);

    }
}
