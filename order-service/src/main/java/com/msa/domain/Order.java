package com.msa.domain;

import com.msa.domain.vo.Money;
import com.msa.domain.vo.OrderStatus;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(name = "price")
    private Money price = new Money(0);

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderProduct> productList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status",nullable = false)
    private OrderStatus orderStatus = OrderStatus.PAYMENT_WAITING;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt = null;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = null;

    public void addTotalPrice(Money price) {
        int value = this.price.getValue() + price.getValue();
        this.price = new Money(value);
    }
}
