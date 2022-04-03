package mock.factory;

import com.msa.domain.Order;

public class OrderFactory {

    public Order getMockOrder() {
        return new Order();
    }
}
