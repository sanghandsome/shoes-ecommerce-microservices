package com.shoes.ecommerce.productservice.repository.specification;

import com.shoes.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProductSpecification {
    public static Specification<Product> searchByName (String name) {
        return (root, criteriaQuery, criteriaBuilder) ->{
            if(StringUtils.hasText(name)) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
            else{
                return criteriaBuilder.conjunction();
            }
        };
    }
}
