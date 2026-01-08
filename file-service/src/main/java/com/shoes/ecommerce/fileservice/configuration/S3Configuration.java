package com.shoes.ecommerce.fileservice.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {
    @Bean
    public S3Client s3Client(S3Properties s3Properties) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(
                s3Properties.getAccessKey(),
                s3Properties.getSecretKey()
        );
        return S3Client.builder()
                .credentialsProvider(() -> awsBasicCredentials)
                .region(software.amazon.awssdk.regions.Region.of(s3Properties.getRegion()))
                .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient(S3Properties s3Properties) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(
                s3Properties.getAccessKey(),
                s3Properties.getSecretKey()
        );
        return S3AsyncClient.builder()
                .credentialsProvider(() -> awsBasicCredentials)
                .region(software.amazon.awssdk.regions.Region.of(s3Properties.getRegion()))
                .build();
    }
}
