package test.com.msa.domain;

import com.msa.domain.OrderProduct;
import com.msa.domain.Product;
import com.msa.domain.vo.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class OrderProductTest extends DomainTestBase{

    @RepeatedTest(3)
    @DisplayName("수량이 0이거나 음수 일 때")
    public void testFailedWhenCountIsNegative() {
        int count = (int) (Math.random() * 100) * -1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OrderProduct.builder()
                    .order(factoryFacade.getMockOrder())
                    .product(factoryFacade.getMockProduct(10000))
                    .count(count).build();
        });
    }

    @Test
    @DisplayName("수량이 0이거나 음수 일 때")
    public void testFailedWhenCountIsZero() {
        int count = 0;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OrderProduct.builder()
                    .order(factoryFacade.getMockOrder())
                    .product(factoryFacade.getMockProduct(10000))
                    .count(count).build();
        });
    }

    @RepeatedTest(3)
    @DisplayName("수량이 양수이면 성공")
    public void testSuccessWhenCountIsPositive() {
        int count = (int) (Math.random() * 100);
        Product product = factoryFacade.getMockProduct(10000);
        int expectedPrice = product.getPrice().getValue() * count;

        OrderProduct orderProduct =
                OrderProduct.builder()
                        .product(product)
                        .order(factoryFacade.getMockOrder())
                        .count(count).build();

        Assertions.assertEquals(new Money(expectedPrice), orderProduct.getPrice());
    }
}
