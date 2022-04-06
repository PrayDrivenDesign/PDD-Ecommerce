package com.msa.presentation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.application.dtos.Requests;
import com.msa.domain.Category;
import com.msa.presentation.factory.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CreateProduct {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProductFactory productFactory;

    @Test
    @DisplayName("상품 생성 - 성공")
    void createProduct() throws Exception {
        Category category = productFactory.createMockCategory();
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", 10000, 100, category.getId());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 성공 / 무료 상품을 대비한 0원의 상품도 가능")
    void createProductWithZeroPrice() throws Exception {
        Category category = productFactory.createMockCategory();
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", 0, 100, category.getId());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 가격은 0원 이상이어야한다")
    void failToCreateProductWithPrice() throws Exception{
        Category category = productFactory.createMockCategory();
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", -1, 100, category.getId());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("가격은 0원 이상이어야 합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 초기 재고는 0개 이상이어야한다.")
    void failToCreateProductWithStock() throws Exception{
        Category category = productFactory.createMockCategory();
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", 100, -1, category.getId());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("재고는 0개 이상이어야합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 존재하지 않는 카테고리")
    void failToCreateProductWithCategory() throws Exception{
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", 100, 100, -1L);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("존재하지 않는 카테고리입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 상품 이름은 비어있을 수 없다.")
    void failToCreateProductWithInvalidName() throws Exception{
        Requests.CreateProductRequest request = productFactory.createProductRequest(" ", 100, 100, -1L);

        mockMvc.perform(post("/products")
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
    void failToCreateProductWithEmptyName() throws Exception{
        Requests.CreateProductRequest request = productFactory.createProductRequest("", 100, 100, -1L);

        mockMvc.perform(post("/products")
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
    void failToCreateProductWithNullName() throws Exception{
        Requests.CreateProductRequest request = productFactory.createProductRequest(null, 100, 100, -1L);

        mockMvc.perform(post("/products")
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
    void failToCreateProductWithNullPrice() throws Exception{
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", null, 100, -1L);

        mockMvc.perform(post("/products")
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
    void failToCreateProductWithNullStock() throws Exception{
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", 10000, null, -1L);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("상품 재고는 Null일 수 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성 - 실패 / 상품 카테고리는 null일 수 없다.")
    void failToCreateProductWithNullCategory() throws Exception{
        Requests.CreateProductRequest request = productFactory.createProductRequest("clock", 10000, 100, null);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("카테고리 Id는 Null일 수 없습니다."))
                .andDo(print());
    }
}
