package com.msa.domain.vo;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum PaymentMethod {
    CARD;

    public static void verifyIfUsable(String paymentMethod) {
        PaymentMethod[] values = PaymentMethod.values();
        String target = paymentMethod.toUpperCase(Locale.ROOT);
        for (PaymentMethod method : values) {
            if (method.toString().equals(target)) {
                return;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 결제 방법입니다.");
    }
}
