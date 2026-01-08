package com.shoes.ecommerce.productservice.dto.request.product;

import com.shoes.ecommerce.productservice.entity.ProductVariant;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class UpdateProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal basePrice;
    private List<ProductVariant> variants;
}
