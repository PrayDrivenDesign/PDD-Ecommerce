package com.msa.domain.vo;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Getter
@Embeddable
public class ProductInfo {
    @Column(name = "product_name",nullable = false)
    private String name;

    @Embedded
    private Money price;

    @Column(name = "product_stock",nullable = false)
    private int stock;
}
