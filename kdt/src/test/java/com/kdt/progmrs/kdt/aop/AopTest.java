package com.kdt.progmrs.kdt.aop;

import com.kdt.progmrs.kdt.order.OrderService;
import com.kdt.progmrs.kdt.voucher.FixedAmountVoucher;
import com.kdt.progmrs.kdt.voucher.VoucherRepository;
import com.kdt.progmrs.kdt.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class AopTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.kdt.progmrs.kdt", "com.kdt.progmrs.kdt.aop"}
    )
    @EnableAspectJAutoProxy
    static class Config {
    }
    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherService voucherService;


    @Test
    public void testApplicationContext() throws Exception {

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
//        voucherRepository.insert(fixedAmountVoucher);
        voucherService.save(fixedAmountVoucher);
    }

}
