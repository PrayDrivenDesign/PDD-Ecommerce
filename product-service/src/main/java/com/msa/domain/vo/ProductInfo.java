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
}
