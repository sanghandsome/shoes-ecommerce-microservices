package com.shoes.ecommerce.productservice.dto.response.product;

import com.shoes.ecommerce.productservice.entity.ProductVariant;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDetailResponse {

    private String name;
    private String brand;
    private String description;
    private BigDecimal basePrice;
    private List<ProductVariant> variants;
}
