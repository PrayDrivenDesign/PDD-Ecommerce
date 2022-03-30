package mock.factory;

import com.msa.domain.Order;
import com.msa.domain.OrderProduct;
import com.msa.domain.Product;

public class OrderProductFactory {
    public OrderProduct getMockOrderProduct(Order order, Product product, int count) {
        return OrderProduct.builder()
                .count(count)
                .product(product)
                .order(order).build();
    }

}
