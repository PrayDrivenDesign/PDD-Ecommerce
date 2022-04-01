package com.msa.domain.vo;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PaymentVendor {
    KAKAO_PAY,
    TOSS;

    private static final Map<String, PaymentVendor> paymentVendorMap =
            Collections.unmodifiableMap(Stream.of(values()) .collect(Collectors.toMap(PaymentVendor::toString, Function.identity())));
    
    public static void verifyIfUsable(String paymentVendor) {
        String target = paymentVendor.toUpperCase(Locale.ROOT);
        if (!paymentVendorMap.containsKey(target)) {
            throw new IllegalArgumentException("지원하지 않는 결제 방법입니다.");
        }
    }
}
