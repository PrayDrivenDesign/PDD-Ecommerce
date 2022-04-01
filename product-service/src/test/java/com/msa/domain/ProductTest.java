package com.msa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product createProduct() {
        String productName = "clock";
        int productStockCount = 100;
        int productPriceValue = 100000;

        Product originProduct = new Product(productName,productPriceValue,productStockCount);

        return originProduct;
    }

    @Test
    @DisplayName("상품 등록자의 가격 수정 - 성공")
    void successToChangeProductPrice() {
        // 기존 product
        Product originProduct = createProduct();

        // 변경
        int targetPrice1 = 1000;
        int targetPrice2 = 2000;
        int targetPrice3 = 3000;
        int targetPrice4 = 4000;
        int targetPrice5 = 0;
        originProduct.editProductPriceInfo(targetPrice1);
        assertTrue(originProduct.getProductInfo().getPrice().getValue() == targetPrice1);

        originProduct.editProductPriceInfo(targetPrice2);
        assertTrue(originProduct.getProductInfo().getPrice().getValue() == targetPrice2);

        originProduct.editProductPriceInfo(targetPrice3);
        assertTrue(originProduct.getProductInfo().getPrice().getValue() == targetPrice3);

        originProduct.editProductPriceInfo(targetPrice4);
        assertTrue(originProduct.getProductInfo().getPrice().getValue() == targetPrice4);

        originProduct.editProductPriceInfo(targetPrice5);
        assertTrue(originProduct.getProductInfo().getPrice().getValue() == targetPrice5);
    }

    @Test
    @DisplayName("상품 등록자의 가격 수정 - 실패 / 상품 등록자의 가격 수정시 새 가격은 음수일 수 없다")
    void failToChangeProductPrice() {
        // 기존 product
        Product originProduct = createProduct();

        // 변경
        assertThrows(IllegalArgumentException.class, () -> {
            originProduct.editProductPriceInfo(-100);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            originProduct.editProductPriceInfo(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            originProduct.editProductPriceInfo(-1000000);
        });
        assertDoesNotThrow(() -> {
            originProduct.editProductStockInfo(1000);
        });
    }

    @Test
    @DisplayName("상품 등록자의 재고 수정 - 성공")
    void successToChangeProductStock() {
        Product originProduct = createProduct();

        int targetStock1 = 0;
        int targetStock2 = 1;
        int targetStock3 = 10;
        int targetStock4 = 100;
        int targetStock5 = 100000;

        originProduct.editProductStockInfo(targetStock1);
        assertTrue(originProduct.getProductInfo().getCurrentStock().getCount() == targetStock1);

        originProduct.editProductStockInfo(targetStock2);
        assertTrue(originProduct.getProductInfo().getCurrentStock().getCount() == targetStock2);

        originProduct.editProductStockInfo(targetStock3);
        assertTrue(originProduct.getProductInfo().getCurrentStock().getCount() == targetStock3);

        originProduct.editProductStockInfo(targetStock4);
        assertTrue(originProduct.getProductInfo().getCurrentStock().getCount() == targetStock4);

        originProduct.editProductStockInfo(targetStock5);
        assertTrue(originProduct.getProductInfo().getCurrentStock().getCount() == targetStock5);
    }

    @Test
    @DisplayName("상품 등록자의 재고 수정 - 실패 / 재고는 0개 이상이어야한다")
    void failToChangeProductStock() {
        Product originProduct = createProduct();

        int targetStock1 = -1;
        int targetStock2 = -100;
        int targetStock3 = -1000000;

        assertThrows(IllegalArgumentException.class, () -> {
            originProduct.editProductPriceInfo(targetStock1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            originProduct.editProductPriceInfo(targetStock2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            originProduct.editProductPriceInfo(targetStock3);
        });
    }

    @Test
    @DisplayName("남아있는 재고 검증 - n개 주문이 가능한 재고가 남아있는지 확인한다.")
    void checkUsableStock() {
        Product product = createProduct();
        int targetStock1 = 1;
        int targetStock2 = 99;
        int targetStock3 = 100;
        int targetStock4 = 101;

        assertTrue(product.checkUsableStock(targetStock1));
        assertTrue(product.checkUsableStock(targetStock2));
        assertTrue(product.checkUsableStock(targetStock3));
        assertFalse(product.checkUsableStock(targetStock4));
    }

    @Test
    @DisplayName("주문을 통한 재고 감소 - 성공")
    void successToOrderProduct() {
        // 현재 재고 100개
        int originStock = 100;
        Product product = createProduct();

        int order1 = 1;
        int order2 = 9;
        int order3 = 90;

        product.orderProduct(order1);
        assertTrue(product.getProductInfo().getCurrentStock().getCount() ==
                (originStock - order1));

        product.orderProduct(order2);
        assertTrue(product.getProductInfo().getCurrentStock().getCount() ==
                (originStock - order1-order2));

        product.orderProduct(order3);
        assertTrue(product.getProductInfo().getCurrentStock().getCount() ==
                (originStock - order1-order2-order3));
    }

    @Test
    @DisplayName("주문을 통한 재고 감소 - 실패 / 해당 주문의 개수만큼의 남은 재고가 없다")
    void failToOrderProduct() {
        // 현재 재고 100개
        int originStock = 100;
        Product product = createProduct();

        int order1 = 100;
        int order2 = 1;

        product.orderProduct(order1);
        assertTrue(product.getProductInfo().getCurrentStock().getCount() ==
                (originStock - order1));

        assertThrows(IllegalArgumentException.class, () -> {
            product.orderProduct(order1);
        });
        assertTrue(product.getProductInfo().getCurrentStock().getCount() == 0);
    }

}