package com.msa.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@EqualsAndHashCode(of = "value")
public class Money {
    private int value;

    public Money(int value) {
        if (value < 0)
            throw new IllegalArgumentException("금액은 0보다 커야 합니다");
        this.value = value;
    }
}
