package mock.factory;

import com.msa.domain.Product;
import com.msa.domain.vo.Money;

public class ProductFactory {

    public Product getMockProduct(int price) {
        return Product.builder()
                .name("MockProduct")
                .price(new Money(price)).build();
    }
}
