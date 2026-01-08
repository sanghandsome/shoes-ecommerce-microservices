package com.shoes.ecommerce.commonlib.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(Exception e, WebRequest request) {
        log.error("Error: ", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(500)
                .message(e.getMessage())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(request.getDescription(false))
                .timestamp(new Date())
                .build();

        return ResponseEntity.internalServerError().body(errorResponse);
    }
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException e, WebRequest request) {
        log.error("Error: ", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .error(e.getErrorCode().getStatus().getReasonPhrase())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(new Date())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
