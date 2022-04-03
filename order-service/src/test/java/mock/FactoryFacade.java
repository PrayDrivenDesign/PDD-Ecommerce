package mock;

import com.msa.domain.Order;
import com.msa.domain.OrderProduct;
import com.msa.domain.Product;
import mock.factory.OrderFactory;
import mock.factory.OrderProductFactory;
import mock.factory.ProductFactory;

public class FactoryFacade {

    private OrderFactory orderFactory = new OrderFactory();
    private ProductFactory productFactory = new ProductFactory();
    private OrderProductFactory orderProductFactory = new OrderProductFactory();

    public Order getMockOrder() {
        return orderFactory.getMockOrder();
    }

    public OrderProduct getMockOrderProduct(Order order, int count) {
        Product product = productFactory.getMockProduct((int) (Math.random() * 1000));

        return orderProductFactory.getMockOrderProduct(
                order,
                product,
                count
        );
    }

    public Product getMockProduct(int price) {
        return productFactory.getMockProduct(price);
    }
}
