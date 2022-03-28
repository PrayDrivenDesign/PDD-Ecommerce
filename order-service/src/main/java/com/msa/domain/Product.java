package com.msa.domain;

import com.msa.domain.vo.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @Column(name = "proudct_id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Embedded
    @Column(name = "price", nullable = false)
    private Money price;

    @Builder
    public Product(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
