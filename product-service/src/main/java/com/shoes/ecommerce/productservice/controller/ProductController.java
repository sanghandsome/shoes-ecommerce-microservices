package com.shoes.ecommerce.productservice.controller;

import com.shoes.ecommerce.productservice.dto.request.product.CreateProductRequest;
import com.shoes.ecommerce.productservice.dto.request.product.UpdateProductRequest;
import com.shoes.ecommerce.productservice.dto.response.ApiResponse;
import com.shoes.ecommerce.productservice.dto.response.PageResponse;
import com.shoes.ecommerce.productservice.dto.response.product.CreateProductResponse;
import com.shoes.ecommerce.productservice.dto.response.product.DeleteProductResponse;
import com.shoes.ecommerce.productservice.dto.response.product.ProductDetailResponse;
import com.shoes.ecommerce.productservice.dto.response.product.UpdateProductResponse;
import com.shoes.ecommerce.productservice.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    ApiResponse<PageResponse<ProductDetailResponse>> getProduct(
            @RequestParam(defaultValue = "1",required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(required = false) String name
            ) {
        var data = productService.getAllProducts(page, size, name);
        return ApiResponse.<PageResponse<ProductDetailResponse>>builder()
                .code(200)
                .message("Get all products")
                .result(data)
                .build();
    }

    @PostMapping
    ApiResponse<CreateProductResponse> createProduct(CreateProductRequest createProductRequest) {
        var data = productService.createProduct(createProductRequest);
        return ApiResponse.<CreateProductResponse>builder()
                .code(201)
                .message("Product created successfully")
                .result(data)
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<UpdateProductResponse> updateProduct(UpdateProductRequest updateProductRequest, @PathVariable UUID id) {
        var data = productService.updateProduct(updateProductRequest);
        return ApiResponse.<UpdateProductResponse>builder()
                .code(201)
                .message("Product created successfully")
                .result(data)
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<DeleteProductResponse> deleteProduct(@PathVariable UUID id) {
        var data = productService.deleteProduct(id);
        return ApiResponse.<DeleteProductResponse>builder()
                .code(201)
                .message("Product created successfully")
                .result(data)
                .build();
    }
}
