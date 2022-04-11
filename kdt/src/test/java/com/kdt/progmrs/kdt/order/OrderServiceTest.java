package com.kdt.progmrs.kdt.order;

import com.kdt.progmrs.kdt.voucher.FixedAmountVoucher;
import com.kdt.progmrs.kdt.voucher.MemoryVoucherRepository;
import com.kdt.progmrs.kdt.voucher.VoucherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository {
        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("오더 생성 테스트 , stub")
    public void createOrder() throws Exception {

        //given

        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        voucherRepository.insert(fixedAmountVoucher);
        OrderService sut = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

        //when
        Order order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        //then
        assertThat(order.totalAmount()).isEqualTo(100);
        assertThat(order.getVoucher()).isNotEmpty();
        assertThat(order.getVoucher().get().getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);

    }

    @Test
    @DisplayName("오더 생성 테스트 , mock")
    public void createOrderByMock() throws Exception {

        //given

        //when

        //then

    }
}
