package com.msa.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.application.dtos.Requests;
import com.msa.domain.Product;
import com.msa.presentation.factory.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UpdateProduct extends ProductFactory{
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("상품 수정 - 성공")
    void updateProduct() throws Exception {
        Product originProduct = createMockProduct();
        String originName = originProduct.getProductInfo().getName();
        int originPrice = originProduct.getProductInfo().getPrice().getValue();
        int originStock = originProduct.getProductInfo().getCurrentStock().getCount();

        Requests.UpdateProductRequest request = updateProductRequest("newName",1,1);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());

        Product found = productRepository.findById(originProduct.getId());

        assertFalse(originName.equals(found.getProductInfo().getName()));
        assertTrue(originPrice != found.getProductInfo().getPrice().getValue());
        assertTrue(originStock != found.getProductInfo().getCurrentStock().getCount());

    }

    @Test
    @DisplayName("상품 생성 - 실패 / 가격은 0원 이상이어야한다")
    void failToUpdateProductWithPrice() throws Exception{
        Product originProduct = createMockProduct();
        Requests.UpdateProductRequest request = updateProductRequest("newName",-1,1);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("가격은 0원 이상이어야 합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 재고는 0개 이상이어야한다.")
    void failToUpdateProductWithStock() throws Exception{
        Product originProduct = createMockProduct();
        Requests.UpdateProductRequest request = updateProductRequest("newName",1,-1);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("재고는 0개 이상이어야합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 상품 이름은 비어있을 수 없다.")
    void failToUpdateProductWithInvalidName() throws Exception{
        Product originProduct = createMockProduct();
        Requests.UpdateProductRequest request = updateProductRequest(" ",1,1);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("유효하지 않은 상품 이름입니다. 상품이름을 다시 확인해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 상품 이름은 공백일 수 없다.")
    void failToUpdateProductWithEmptyName() throws Exception{
        Product originProduct = createMockProduct();
        Requests.UpdateProductRequest request = updateProductRequest("",1,1);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("유효하지 않은 상품 이름입니다. 상품이름을 다시 확인해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 상품 이름은 null일 수 없다.")
    void failToUpdateProductWithNullName() throws Exception{
        Product originProduct = createMockProduct();
        Requests.UpdateProductRequest request = updateProductRequest(null,1,1);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("유효하지 않은 상품 이름입니다. 상품이름을 다시 확인해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 상품 가격은 null일 수 없다.")
    void failToUpdateProductWithNullPrice() throws Exception{
        Product originProduct = createMockProduct();
        Requests.UpdateProductRequest request = updateProductRequest("newName",null,1);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("상품 가격은 Null일 수 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 상품 재고는 null일 수 없다.")
    void failToUpdateProductWithNullStock() throws Exception{
        Product originProduct = createMockProduct();
        Requests.UpdateProductRequest request = updateProductRequest("newName",1,null);

        mockMvc.perform(put("/products/{productId}", originProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("상품 재고는 Null일 수 없습니다."))
                .andDo(print());
    }

}
