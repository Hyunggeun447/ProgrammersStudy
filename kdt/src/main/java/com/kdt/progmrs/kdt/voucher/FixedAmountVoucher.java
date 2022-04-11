package com.kdt.progmrs.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 100000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) throw new IllegalArgumentException("not negative");
        if (amount == 0) throw new IllegalArgumentException("not Zero");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("not MAX");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return discountedAmount >= 0 ? discountedAmount : 0;
    }
}
