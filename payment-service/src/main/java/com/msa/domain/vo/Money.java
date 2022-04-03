package com.msa.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "value")
public class Money {
    @Column(name = "payment_price",nullable = false)
    private int value;

    public Money(int value) {
        verifyValueOfMoney(value);
        this.value = value;
    }

    private void verifyValueOfMoney(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }
    }
}
