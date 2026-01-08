package com.shoes.ecommerce.fileservice.dto;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "aws.s3")

public class S3Properties {
    private String region;
    private String accessKey;
    private String secretKey;
    private Long maxFileSize = 300L * 1024 * 1024;
    private String bucketName;
}
