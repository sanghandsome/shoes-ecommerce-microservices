package com.shoes.ecommerce.fileservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long fileSize;
    private int displayOrder;
}
