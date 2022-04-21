package com.msa.domain;

import com.msa.common.ErrorMessages;
import com.msa.domain.vo.ProductInfo;
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
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "product_version")
    private int version=0;

    @Builder
    public Product(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    // 상품 등록자의 가격 수정
    public void editProductPriceInfo(int newPrice) {
        this.productInfo.editPrice(newPrice);
    }

    // 상품 등록자의 재고 수정 - 재고는 음수일 수 없다
    public void editProductStockInfo(int newStockCount) {
        this.productInfo.editStockCount(newStockCount);
    }

    // 상품 등록자의 상품명 수정
    public void editProductNameInfo(String newName) {
        this.productInfo.editName(newName);
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
            throw new IllegalArgumentException(ErrorMessages.NOT_ENOUGH_STOCK_EXCEPTION);
        }
        this.productInfo.reduceStock(counts);
    }

    public void editProductInfo(String name, Integer price, Integer stock) {
        //추후에 단일 수정이 필요할수도 있다고 생각해서 매서드 자체는 우선 유지
        this.editProductNameInfo(name);
        this.editProductPriceInfo(price);
        this.editProductStockInfo(stock);
    }
}

