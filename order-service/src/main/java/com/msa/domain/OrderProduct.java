package com.msa.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    @Id
    @Column(name = "order_proudct_id")
    private Long id;

    @ManyToOne(optional = false)
    private Order order;

    @ManyToOne(optional = false)
    private Product product;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private Money money;

    @Builder
    public OrderProduct(Order order, Product product, int count) {
        this.order = order;
        this.product = product;
        this.count = count;
        this.money = calculateTotalPrice();
    }

    public Money calculateTotalPrice() {
        return new Money(product.getPrice().getValue() * count);
    }
}