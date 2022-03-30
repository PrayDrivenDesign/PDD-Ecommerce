package com.msa.domain.vo;

import lombok.AllArgsConstructor;
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

    public void editPrice(Money newPrice) {
        this.price = newPrice;
    }

    public void editStockCount(Stock newStock) {
        this.currentStock = newStock;
    }

    public void reduceStock(int counts) {
        this.currentStock = new Stock(currentStock.getCount() - counts);
        if (currentStock.getCount() < 0) {
            throw new IllegalArgumentException("재고가 음수가 되는 경우 발생!!");
        }
    }
}
