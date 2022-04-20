package com.msa.domain.vo;

import com.msa.common.ErrorMessages;
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
    @Column(name = "product_price",nullable = false)
    private int value;

    public Money(int value) {
        verifyValueOfMoney(value);
        this.value = value;
    }

    private void verifyValueOfMoney(int value) {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMessages.NOT_VALID_PRICE_EXCEPTION);
        }
    }
}
