package com.msa.domain.vo;

import com.msa.common.ErrorMessages;
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
            throw new IllegalArgumentException(ErrorMessages.NOT_VALID_STOCK_EXCEPTION);
        }
    }
}
