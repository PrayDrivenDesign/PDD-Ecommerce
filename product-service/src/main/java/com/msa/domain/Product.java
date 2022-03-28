package com.msa.domain;

import com.msa.domain.vo.Money;
import com.msa.domain.vo.ProductInfo;
import com.msa.domain.vo.Stock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private ProductInfo productInfo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "product_version")
    private int version=0;

    public Product(ProductInfo productInfo) {
        this.id = null;
        this.productInfo = productInfo;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 상품 등록자의 가격 수정
    public void editProductPriceInfo(int newPrice) {
        this.productInfo.editPrice(new Money(newPrice));
    }

    // 상품 등록자의 재고 수정 - 재고는 음수일 수 없다
    public void editProductStockInfo(int newStockCount) {
        this.productInfo.editStockCount(new Stock(newStockCount));
    }

    //남은 재고 검증 - 주문할 수 있는 n개의 재고가 남아있는지 확인
    public boolean checkUsableStock(int orderedProductCount) {
        return this.productInfo.getCurrentStock().getCount() >= orderedProductCount;
    }

    //주문을 통한 재고 감소
    public void orderProduct(int counts) {
        // order에서 먼저 남은 재고 검증(checkUsableStock)을 통해 주문 -> 동시적으로 주문이 일어난 경우를 대비해 주문 처리할 때 한번 더 검증
        int remainingStock = this.productInfo.getCurrentStock().getCount();
        if (remainingStock < counts) {
            throw new IllegalArgumentException("현재 남은 재고가 주문량보다 적습니다.");
        }
        this.productInfo.reduceStock(counts);
    }

}

