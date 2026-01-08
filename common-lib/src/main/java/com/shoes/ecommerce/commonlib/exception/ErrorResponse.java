package com.shoes.ecommerce.commonlib.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ErrorResponse {
    private int status;
    private String message;
    private String error;
    private String path;
    private Date timestamp;
}
