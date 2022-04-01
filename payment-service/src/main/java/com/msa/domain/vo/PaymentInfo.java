package com.msa.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {
    @Column(name = "payment_order_id")
    private Long orderId;

    @Embedded
    @Column(nullable = false)
    private Money amounts;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_vendor", nullable = false)
    private PaymentVendor paymentVendor;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Builder
    public PaymentInfo(Long orderId, int amountValue, String paymentMethod, String paymentVendor) {
        this.orderId = orderId;
        this.amounts = new Money(amountValue);

        PaymentMethod.verifyIfUsable(paymentMethod);
        this.paymentMethod = PaymentMethod.valueOf(paymentMethod.toUpperCase(Locale.ROOT));

        PaymentVendor.verifyIfUsable(paymentVendor);
        this.paymentVendor = PaymentVendor.valueOf(paymentVendor.toUpperCase(Locale.ROOT));

        this.paymentDate = LocalDateTime.now();
    }
}
