package com.msa.domain.vo;

import com.msa.common.ErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    @Column(name = "product_name",nullable = false)
    private String name;

    @Embedded
    private Money price;

    @Embedded
    private Stock currentStock;

    @Builder
    public ProductInfo(String name, int price, int currentStock) {
        this.name = name;
        this.price = new Money(price);
        this.currentStock = new Stock(currentStock);
    }

    public void editPrice(int newPriceValue) {
        this.price = new Money(newPriceValue);
    }

    public void editStockCount(int newStockValue) {
        this.currentStock = new Stock(newStockValue);
    }

    public void editName(String newName) {
        this.name = newName;
    }

    public void reduceStock(int counts) {
        this.currentStock = new Stock(currentStock.getCount() - counts);
        if (currentStock.getCount() < 0) {
            throw new IllegalArgumentException(ErrorMessages.NEGATIVE_STOCK_EXCEPTION);
        }
    }
}
