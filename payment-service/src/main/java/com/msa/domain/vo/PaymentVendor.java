package com.msa.domain.vo;

import java.util.Locale;

public enum PaymentVendor {
    KAKAO_PAY,
    TOSS;

    public static void verifyIfUsable(String paymentVendor) {
        PaymentVendor[] values = PaymentVendor.values();
        String target = paymentVendor.toUpperCase(Locale.ROOT);
        for (PaymentVendor vendor : values) {
            if (vendor.toString().equals(target)) {
                return;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 결제사입니다.");
    }
}
