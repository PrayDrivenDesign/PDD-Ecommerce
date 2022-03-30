package com.msa.domain;

import com.msa.domain.vo.Money;
import lombok.AccessLevel;
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

    @PreUpdate
    @PrePersist
    private void updateTotalPrice(List<OrderProduct> products) {
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
