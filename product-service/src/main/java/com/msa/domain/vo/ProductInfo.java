package com.msa.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

        /**
         * a가 orderProduct 호출
         * 이 때 b가 orderProduct호출하여 먼저 처리되고 남은 재고가 0
         * 이 후 a가 다시진행하면??
         * a는 재고가 남아있다고 확인했으니 그대로 수행하는데 이를 막기 위한 한번 더 방어
         * */
        if (currentStock.getCount() < 0) {
            throw new IllegalArgumentException("재고가 음수가 되는 경우 발생!!");
        }
    }
}
