package com.msa.domain.vo;

import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Getter
public enum PaymentMethod {
    CARD;

    private static final Map<String, PaymentMethod> paymentMethodMap =
            Collections.unmodifiableMap(Stream.of(values()) .collect(Collectors.toMap(PaymentMethod::toString, Function.identity())));

    public static void verifyIfUsable(String paymentMethod) {
        String target = paymentMethod.toUpperCase(Locale.ROOT);
        if (!paymentMethodMap.containsKey(target)) {
            throw new IllegalArgumentException("지원하지 않는 결제 방법입니다.");
        }
    }
}
