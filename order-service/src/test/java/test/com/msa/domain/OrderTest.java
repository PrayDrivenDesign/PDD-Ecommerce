package test.com.msa.domain;

import com.msa.domain.Order;
import com.msa.domain.OrderProduct;
import com.msa.domain.vo.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class OrderTest extends DomainTestBase {

    @Test
    @DisplayName("Total Price 계산 테스트")
    public void testOrderTotalPrice() {
        ArrayList<OrderProduct> productList = new ArrayList<>();
        Order order = factoryFacade.getMockOrder();
        for (int i = 0; i < 10; i++) {
            OrderProduct orderProduct = factoryFacade.getMockOrderProduct(order, 1);
            productList.add(orderProduct);
        }
        int expectedPrice = productList.stream().map(
                it -> it.getPrice().getValue()
        ).reduce(0, Integer::sum);
        Assertions.assertEquals(
                order.getPrice(),
                new Money(expectedPrice)
        );
    }
}
