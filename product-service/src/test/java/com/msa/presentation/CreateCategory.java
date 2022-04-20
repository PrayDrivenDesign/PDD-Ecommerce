package com.msa.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.application.dtos.Requests;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CreateCategory extends ProductFactory {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("카테고리 생성 성공")
    void createCategory() throws Exception {
        Requests.CreateCategoryRequest request = createCategoryRequest("foods");

        mockMvc.perform(post("/products/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 생성 실패 - 이미 존재하는 카테고리명")
    void failToCreateCategoryWithName() throws Exception {
        createMockCategory();
        Requests.CreateCategoryRequest request = createCategoryRequest("tempCategory");

        mockMvc.perform(post("/products/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("message").value("이미 존재하는 카테고리입니다."))
                .andDo(print());
    }

}
