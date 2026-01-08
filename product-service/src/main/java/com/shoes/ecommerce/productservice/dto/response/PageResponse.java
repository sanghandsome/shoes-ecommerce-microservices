package com.shoes.ecommerce.productservice.dto.response;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PageResponse<T> {
    private int currentPage;
    private int pageSizes;
    private int totalPages;
    private long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();

}
