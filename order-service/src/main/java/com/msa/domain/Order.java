package com.msa.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(name = "price")
    private Money price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderProduct> productList;

    @CreatedDate
    private LocalDateTime createdAt = null;

    @LastModifiedDate
    private LocalDateTime updatedAt = null;

    @Builder
    public Order(List<OrderProduct> productList) {
        addProducts(productList);
    }

    private void addProducts(List<OrderProduct> products) {
        this.productList = products;
        Money price = calculateTotalPrice(this.productList);
        updateTotalPrice(price);
    }

    private Money calculateTotalPrice(List<OrderProduct> products) {
        int totalPrice = products.stream()
                .map(OrderProduct::getPrice)
                .map(Money::getValue).reduce(0, Integer::sum);
        return new Money(totalPrice);
    }

    public void updateTotalPrice(Money price) {
        this.price = price;
    }
}
