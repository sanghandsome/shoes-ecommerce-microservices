package com.shoes.ecommerce.productservice.mapper;

import com.shoes.ecommerce.productservice.dto.request.product.CreateProductRequest;
import com.shoes.ecommerce.productservice.dto.request.product.UpdateProductRequest;
import com.shoes.ecommerce.productservice.dto.response.product.CreateProductResponse;
import com.shoes.ecommerce.productservice.dto.response.product.DeleteProductResponse;
import com.shoes.ecommerce.productservice.dto.response.product.ProductDetailResponse;
import com.shoes.ecommerce.productservice.dto.response.product.UpdateProductResponse;
import com.shoes.ecommerce.productservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProductFromCreat(CreateProductRequest createProductRequest);
    Product toProductFromUpdate(UpdateProductRequest updateProductRequest);
    CreateProductResponse toCreateProductResponse(Product product);
    ProductDetailResponse toProductDetailResponse(Product product);
    UpdateProductResponse toUpdateProductResponse(Product product);
    DeleteProductResponse toDeleteProductResponse(Product product);
}
