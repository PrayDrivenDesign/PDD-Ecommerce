package com.msa.domain.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class Stock {
    @Column(name = "product_stock", nullable = false)
    private int count;

    public Stock(int count) {
        verifyCountOfStock(count);
        this.count = count;
    }

    private void verifyCountOfStock(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("재고는 0개 이상이어야합니다.");
        }
    }
}
