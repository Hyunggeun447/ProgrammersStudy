package com.kdt.progmrs.kdt;

import com.kdt.progmrs.kdt.voucher.FixedAmountVoucher;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTesterTest {

    private final static Logger logger = LoggerFactory.getLogger(OrderTesterTest.class);

    @BeforeAll
    static void setup() {
        logger.info("@BeforeALl - 한번 실행");
    }

    @BeforeEach
    void init(){
        logger.info("@BeforeEach");
    }



    @Test
    public void nameAssertEqual() throws Exception {
        assertEquals(2,1+1);
    }

    @Test
    @DisplayName("good test")
    public void testDiscount() throws Exception {

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertEquals(900, fixedAmountVoucher.discount(1000));
    }

    @Test
    @DisplayName("할인금액은 마이너스 불가능")
//    @Disabled
    public void testWithMinus() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }
    @Test
    @DisplayName("최종금액은 마이너스 불가능")
    public void testMinusDiscount() throws Exception {

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0, fixedAmountVoucher.discount(900));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    public void testVoucherCreation() throws Exception {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 1000000)));

    }



}