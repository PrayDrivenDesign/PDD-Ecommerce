package com.msa.domain.vo;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;



@Getter
public enum PaymentMethod {
    CARD;

    private static final Set<String> paymentMethodSet = Arrays.stream(PaymentMethod.values()).map(PaymentMethod::toString).collect(Collectors.toSet());


    public static void verifyIfUsable(String paymentMethod) {
        String target = paymentMethod.toUpperCase(Locale.ROOT);
        if (!paymentMethodSet.contains(target)) {
            throw new IllegalArgumentException("지원하지 않는 결제 방법입니다.");
        }
    }
}
