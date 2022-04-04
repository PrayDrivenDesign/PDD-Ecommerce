package com.msa.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "product_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {
    @Id
    @Column(name = "product_categories_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
    }
}
