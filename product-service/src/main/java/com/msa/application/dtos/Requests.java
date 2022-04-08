package com.msa.application.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Requests {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateProductRequest {
        @NotBlank(message = "유효하지 않은 상품 이름입니다. 상품이름을 다시 확인해주세요.")
        private String name;

        @NotNull(message = "상품 가격은 Null일 수 없습니다.")
        private Integer price;

        @NotNull(message = "상품 재고는 Null일 수 없습니다.")
        private Integer stock;

        @NotNull(message = "카테고리 Id는 Null일 수 없습니다.")
        private Long categoryId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateProductRequest {
        @NotBlank(message = "유효하지 않은 상품 이름입니다. 상품이름을 다시 확인해주세요.")
        private String name;

        @NotNull(message = "상품 가격은 Null일 수 없습니다.")
        private Integer price;

        @NotNull(message = "상품 재고는 Null일 수 없습니다.")
        private Integer stock;
    }

}
