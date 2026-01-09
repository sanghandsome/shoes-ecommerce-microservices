package com.shoes.ecommerce.indentityservice.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter

public class BaseEntity {
    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreatedBy
    private String  createdBy;

    @LastModifiedBy
    private String updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
