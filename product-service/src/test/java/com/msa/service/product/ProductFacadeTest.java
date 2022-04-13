package com.msa.service.product;

import com.msa.application.ProductFacade;
import com.msa.application.dtos.Requests;
import com.msa.application.events.EventUseCase;
import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.ProductCategory;
import com.msa.domain.service.CategoryService;
import com.msa.domain.service.ProductCategoryService;
import com.msa.domain.service.ProductService;
import com.msa.domain.vo.ProductInfo;
import com.msa.infrastructure.kafka.Events;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@Transactional
public class ProductFacadeTest extends ProductBase {
    @Mock
    ProductService productService;
    @Mock
    CategoryService categoryService;
    @Mock
    ProductCategoryService productCategoryService;
    @Mock
    EventUseCase eventUseCase;
    @InjectMocks
    ProductFacade productFacade;

    @Test
    @DisplayName("상품 생성시 생성 이벤트 produce")
    void invokeCreatedEvent() {
        //given
        ProductInfo info = createMockProductInfo("product", 100, 100);
        Product product = createMockProduct(info);
        ReflectionTestUtils.setField(product, "id", 1L);
        Category category = createMockCategory("category");
        ProductCategory productCategory = createMockProductCategory(product, category);

        given(categoryService.findById(anyLong())).willReturn(category);
        given(productService.createProduct(anyString(), anyInt(), anyInt())).willReturn(product);
        given(productCategoryService.createProductCategory(product, category)).willReturn(productCategory);
        doNothing().when(eventUseCase).sendCreatedEvent(product.getId(), product.getProductInfo().getName(), product.getProductInfo().getPrice().getValue());

        //when
        productFacade.createProduct(createMockCreateRequest());

        //then
        verify(eventUseCase).sendCreatedEvent(product.getId(), product.getProductInfo().getName(), product.getProductInfo().getPrice().getValue());
    }

    @Test
    @DisplayName("상품 수정시 수정 이벤트 produce")
    void invokeUpdatedEvent() {
        //given
        ProductInfo info = createMockProductInfo("product", 100, 100);
        Product product = createMockProduct(info);
        ReflectionTestUtils.setField(product, "id", 1L);
        Requests.UpdateProductRequest request = createMockUpdateRequest();

        ProductInfo updateInfo = createMockProductInfo("product", 101, 101);
        Product updatedProduct = createMockProduct(updateInfo);
        ReflectionTestUtils.setField(updatedProduct, "id", 1L);

        given(productService.updateProduct(product.getId(),request.getName(),request.getPrice(),request.getStock())).willReturn(updatedProduct);
        doNothing().when(eventUseCase).sendUpdatedEvent(updatedProduct.getId(), updatedProduct.getProductInfo().getName(), updatedProduct.getProductInfo().getPrice().getValue());

        //when
        productFacade.updateProduct(product.getId(), request);

        //then
        verify(eventUseCase).sendUpdatedEvent(updatedProduct.getId(),updatedProduct.getProductInfo().getName(),updatedProduct.getProductInfo().getPrice().getValue());
    }
}
