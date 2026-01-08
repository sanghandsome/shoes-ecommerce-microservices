package com.shoes.ecommerce.productservice.service;

import com.shoes.ecommerce.commonlib.exception.AppException;
import com.shoes.ecommerce.productservice.dto.request.product.CreateProductRequest;
import com.shoes.ecommerce.productservice.dto.request.product.UpdateProductRequest;
import com.shoes.ecommerce.productservice.dto.response.PageResponse;
import com.shoes.ecommerce.productservice.dto.response.product.CreateProductResponse;
import com.shoes.ecommerce.productservice.dto.response.product.DeleteProductResponse;
import com.shoes.ecommerce.productservice.dto.response.product.ProductDetailResponse;
import com.shoes.ecommerce.productservice.dto.response.product.UpdateProductResponse;
import com.shoes.ecommerce.productservice.entity.Product;
import com.shoes.ecommerce.productservice.mapper.ProductMapper;
import com.shoes.ecommerce.productservice.repository.ProductRepository;
import com.shoes.ecommerce.productservice.repository.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import static com.shoes.ecommerce.commonlib.exception.ErrorCode.PRODUCT_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Cacheable(value = "product" , key = "#id")
    public PageResponse<ProductDetailResponse> getAllProducts(int page, int size, String name) {
        log.info("Getting all products");
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page-1, size, sort);
        Specification<Product> specification = Specification.allOf(ProductSpecification.searchByName(name));
        Page<Product> productPage = productRepository.findAll(specification, pageable);
        List<Product> products = productPage.getContent();
        List<ProductDetailResponse> responses = products.stream()
                .map(productMapper::toProductDetailResponse)
                .toList();
        return  PageResponse.<ProductDetailResponse>builder()
                .currentPage(page)
                .pageSizes(pageable.getPageSize())
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .data(responses)
                .build();
    }

    @CacheEvict(allEntries = true , value = "allProducts")
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        Product product = productMapper.toProductFromCreat(createProductRequest);
        product =  productRepository.save(product);
        log.info("Create product successfully");
        return productMapper.toCreateProductResponse(product);
    }

    @CachePut(value = "product" , key = "#id")
    public UpdateProductResponse updateProduct(UpdateProductRequest updateProductRequest) {
        Product product = productMapper.toProductFromUpdate(updateProductRequest);
        product =  productRepository.save(product);
        log.info("Update product successfully");
        return productMapper.toUpdateProductResponse(product);
    }

    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "allProducts", allEntries = true)
    })
    public DeleteProductResponse deleteProduct(UUID id) {
        log.info("Delete product successfully");
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        return productMapper.toDeleteProductResponse(product);
    }
}
