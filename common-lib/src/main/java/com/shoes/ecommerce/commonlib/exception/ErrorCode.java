package com.shoes.ecommerce.commonlib.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    FILE_INVALID(400,"File can not empty" , HttpStatus.BAD_REQUEST ),
    PRODUCT_NOT_FOUND(400,"Product not found" , HttpStatus.NOT_FOUND ),
    FILE_SIZE_EXCEEDED(400,"File Size Exceeded",HttpStatus.BAD_REQUEST),
    ;
    private final int code;
    private final String message;
    private final HttpStatus status;

}
