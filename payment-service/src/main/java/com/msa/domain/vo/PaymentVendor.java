package com.msa.domain.vo;

import java.util.*;
import java.util.stream.Collectors;

public enum PaymentVendor {
    KAKAO_PAY,
    TOSS;

    private static final Set<String> paymentVendorSet = Arrays.stream(PaymentVendor.values()).map(PaymentVendor::toString).collect(Collectors.toCollection(HashSet::new));

    public static void verifyIfUsable(String paymentVendor) {
        String target = paymentVendor.toUpperCase(Locale.ROOT);
        if (!paymentVendorSet.contains(target)) {
            throw new IllegalArgumentException("지원하지 않는 결제 방법입니다.");
        }
    }
}
