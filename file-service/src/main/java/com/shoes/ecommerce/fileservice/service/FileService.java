package com.shoes.ecommerce.fileservice.service;

import com.shoes.ecommerce.commonlib.exception.AppException;
import com.shoes.ecommerce.commonlib.exception.ErrorCode;
import com.shoes.ecommerce.fileservice.dto.FileResponse;
import com.shoes.ecommerce.fileservice.dto.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Slf4j
@Service

public class FileService {
    private final S3Properties s3Properties;
    private final S3Client s3Client;
    private final S3AsyncClient s3AsyncClient;

    public FileResponse uploadSingleFile(MultipartFile file) throws IOException {
        if(file.isEmpty())
            throw new AppException(ErrorCode.FILE_INVALID);

        String key = generateKeyName(file);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .key(key)
                .build();
        RequestBody body = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
        s3Client.putObject(request, body);

        String urlImages = "https://" + s3Properties.getBucketName() + ".s3." + s3Properties.getRegion() + ".amazonaws.com/" + key;

        return FileResponse.builder()
                .fileDownloadUri(urlImages)
                .fileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .build();
    }

    public CompletableFuture<List<FileResponse>> uploadFileAsync(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            log.error("Upload files is null or empty");
            return CompletableFuture.completedFuture(new ArrayList<>());
        }

        String bucketName = s3Properties.getBucketName();
        String region = s3Properties.getRegion();

        AtomicInteger orderCounter = new AtomicInteger(0);

        List<CompletableFuture<FileResponse>> uploadFutures = files.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    validateUploadFile(file);

                    int displayOrder = orderCounter.incrementAndGet();

                    String key = generateKeyName(file);
                    log.info("Starting asynchronous upload of file: {} to bucket: {}", key, bucketName);

                    try {
                        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .contentType(file.getContentType())
                                .contentLength(file.getSize())
                                .build();

                        AsyncRequestBody requestBody = AsyncRequestBody.fromBytes(file.getBytes());

                        return s3AsyncClient.putObject(putObjectRequest, requestBody)
                                .thenApply(response ->  {
                                    String imageUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
                                    return FileResponse.builder()
                                            .fileName(file.getOriginalFilename())
                                            .fileType(file.getContentType())
                                            .fileSize(file.getSize())
                                            .fileDownloadUri(imageUrl)
                                            .displayOrder(displayOrder)
                                            .build();
                                })
                                .exceptionally(throwable -> {
                                    log.error("Failed to upload file: {} to bucket: {}. Error: {}", key, bucketName, throwable.getMessage());
                                    throw new RuntimeException("Upload failed", throwable);
                                });
                    } catch (IOException e) {
                        log.error("Failed to read file bytes: {}", e.getMessage());
                        throw new RuntimeException();
                    }
                })
                .toList();

        return CompletableFuture.allOf(uploadFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> uploadFutures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .toList());
    }

    private void validateUploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            log.warn("Skipping empty file: {}", file.getOriginalFilename());
            return;
        }

        if (file.getSize() > s3Properties.getMaxFileSize()) {
            log.warn("Upload file size is greater than s3 file size");
            throw new AppException(ErrorCode.FILE_SIZE_EXCEEDED);
        }
    }

    public static String generateKeyName(MultipartFile file) {
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        return UUID.randomUUID() + "_" + originalFilename
                .substring(originalFilename.lastIndexOf("."));
    }
}
