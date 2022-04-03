package com.msa.domain;

import com.msa.domain.vo.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @Column(name = "order_proudct_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "order_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(optional = false)
    private Product product;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private Money price;

    @Builder
    public OrderProduct(Order order, Product product, int count) {
        if (count <= 0)
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        this.product = product;
        this.count = count;
        this.price = calculateTotalPrice();
        addOrder(order);
    }

    public void addOrder(Order order) {
        this.order = order;
        this.order.getProductList().add(this);
        this.order.addTotalPrice(this.price);
    }

    public Money calculateTotalPrice() {
        return new Money(product.getPrice().getValue() * count);
    }
}
