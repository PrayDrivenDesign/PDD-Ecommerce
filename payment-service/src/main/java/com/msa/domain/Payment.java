package com.msa.domain;

import com.msa.domain.vo.PaymentInfo;
import com.msa.domain.vo.PaymentMethod;
import com.msa.domain.vo.PaymentVendor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private PaymentInfo paymentInfo;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Payment(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
