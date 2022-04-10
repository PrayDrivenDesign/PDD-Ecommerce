package com.msa.presentation.controller;

import com.msa.application.ProductFacade;

import com.msa.application.dtos.Requests;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductFacade productFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createProduct(@RequestBody @Valid Requests.CreateProductRequest request) {
        Long productId = productFacade.createProduct(request);
        return ResponseEntity.ok().body(productId);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody @Valid Requests.UpdateProductRequest request) {
        productFacade.updateProduct(productId, request);
        return ResponseEntity.ok().body(productId);
    }
}
