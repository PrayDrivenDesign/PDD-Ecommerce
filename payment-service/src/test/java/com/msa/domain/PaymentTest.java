package com.msa.domain;

import com.msa.domain.vo.PaymentInfo;
import com.msa.domain.vo.PaymentMethod;
import com.msa.domain.vo.PaymentVendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private static Long orderId = 1L;
    private static int amountValue = 100;
    private static int minusPrice = -100;

    private Payment createPayment(PaymentInfo paymentInfo) {
         return Payment.builder()
                .paymentInfo(paymentInfo)
                .build();
    }

    @Test
    @DisplayName("payment 생성 - 성공 / 결제 수단이 대문자인경우")
    void successToCreatePaymentWithUppersMethod() {
        assertDoesNotThrow(() -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("CARD")
                    .paymentVendor("KAKAO_PAY")
                    .build();

            Payment payment = createPayment(paymentInfo);

            assertTrue(payment.getPaymentInfo().getOrderId() == orderId);
            assertTrue(payment.getPaymentInfo().getPaymentMethod() == PaymentMethod.CARD);
            assertTrue(payment.getPaymentInfo().getPaymentVendor() == PaymentVendor.KAKAO_PAY);
        });
    }

    @Test
    @DisplayName("payment 생성 - 성공 / 결제 수단이 소문자인경우")
    void successToCreatePaymentWithLowersMethod() {
        assertDoesNotThrow(() -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("card")
                    .paymentVendor("KAKAO_PAY")
                    .build();
            Payment payment = createPayment(paymentInfo);

            assertTrue(payment.getPaymentInfo().getOrderId() == orderId);
            assertTrue(payment.getPaymentInfo().getPaymentMethod() == PaymentMethod.CARD);
            assertTrue(payment.getPaymentInfo().getPaymentVendor() == PaymentVendor.KAKAO_PAY);
        });
    }

    @Test
    @DisplayName("payment 생성 - 성공 / 결제 수단이 대소문자 조합인경우")
    void successToCreatePaymentWithMixedMethod() {
        assertDoesNotThrow(() -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("CaRd")
                    .paymentVendor("KAKAO_PAY")
                    .build();
            Payment payment = createPayment(paymentInfo);

            assertTrue(payment.getPaymentInfo().getOrderId() == orderId);
            assertTrue(payment.getPaymentInfo().getPaymentMethod() == PaymentMethod.CARD);
            assertTrue(payment.getPaymentInfo().getPaymentVendor() == PaymentVendor.KAKAO_PAY);
        });
    }

    @Test
    @DisplayName("payment 생성 - 성공 / 결제사가 대문자인경우")
    void successToCreatePaymentWithUppersVendors() {
        assertDoesNotThrow(() -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("CARD")
                    .paymentVendor("KAKAO_PAY")
                    .build();
            Payment payment = createPayment(paymentInfo);

            assertTrue(payment.getPaymentInfo().getOrderId() == orderId);
            assertTrue(payment.getPaymentInfo().getPaymentMethod() == PaymentMethod.CARD);
            assertTrue(payment.getPaymentInfo().getPaymentVendor() == PaymentVendor.KAKAO_PAY);
        });
    }

    @Test
    @DisplayName("payment 생성 - 성공 / 결제사가 소문자인경우")
    void successToCreatePaymentWithLowersVendors() {
        assertDoesNotThrow(() -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("CARD")
                    .paymentVendor("kakao_pay")
                    .build();
            Payment payment = createPayment(paymentInfo);

            assertTrue(payment.getPaymentInfo().getOrderId() == orderId);
            assertTrue(payment.getPaymentInfo().getPaymentMethod() == PaymentMethod.CARD);
            assertTrue(payment.getPaymentInfo().getPaymentVendor() == PaymentVendor.KAKAO_PAY);
        });
    }

    @Test
    @DisplayName("payment 생성 - 성공 / 결제사가 대소문자 조합인경우")
    void successToCreatePaymentWithMixedVendors() {
        assertDoesNotThrow(() -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("CARD")
                    .paymentVendor("KaKaO_PAY")
                    .build();
            Payment payment = createPayment(paymentInfo);

            assertTrue(payment.getPaymentInfo().getOrderId() == orderId);
            assertTrue(payment.getPaymentInfo().getPaymentMethod() == PaymentMethod.CARD);
            assertTrue(payment.getPaymentInfo().getPaymentVendor() == PaymentVendor.KAKAO_PAY);
        });
    }

    // 실패
    @Test
    @DisplayName("payment 생성 - 실패 / 결제 금액이 0보다 작은 경우")
    void failToCreatePaymentWithMinusPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(minusPrice)
                    .paymentMethod("CARD")
                    .paymentVendor("KAKAO_PAY")
                    .build();
            Payment payment = createPayment(paymentInfo);
            assertTrue(payment.getPaymentInfo().getOrderId() == orderId);
            assertTrue(payment.getPaymentInfo().getPaymentMethod() == PaymentMethod.CARD);
            assertTrue(payment.getPaymentInfo().getPaymentVendor() == PaymentVendor.KAKAO_PAY);
        });
    }

    @Test
    @DisplayName("payment 생성 - 실패 / 지원하지 않는 결제 방법")
    void failToCreatePaymentWithUnsupportedMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("visit")
                    .paymentVendor("KAKAO_PAY")
                    .build();
            Payment payment = createPayment(paymentInfo);
        });
    }

    @Test
    @DisplayName("payment 생성 - 실패 / 지원하지 않는 결제사")
    void failToCreatePaymentWithUnsupportedVendor() {
        assertThrows(IllegalArgumentException.class, () -> {
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .orderId(orderId)
                    .amountValue(amountValue)
                    .paymentMethod("CARD")
                    .paymentVendor("PDD_pay")
                    .build();
            Payment payment = createPayment(paymentInfo);
        });
    }
}