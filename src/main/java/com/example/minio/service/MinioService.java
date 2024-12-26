package com.example.minio.service;

import com.example.minio.dto.FileInfo;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    public String getPresignedUrl(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 生成 Presigned URL，有效期為 30分
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket("minio-demo")
                            .object(fileName)
                            .expiry(30, TimeUnit.MINUTES)
                            .build());
        } catch (Exception e) {
            log.warn("minio get presignedUrl failed.", e);
            throw e;
        }

    }

    public String getFileUrl(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket("minio-demo")
                        .object(fileName)
                        .method(Method.GET)
                        .build()
        );
    }

    public List<FileInfo> listObjectUrls() throws Exception {
        List<FileInfo> fileInfos = new ArrayList<>();

        // 注意 prefix
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket("minio-demo").prefix("uploads/").build()
        );

        for (Result<Item> result : results) {
            Item item = result.get();

            String presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket("minio-demo")
                            .object(item.objectName())
                            .method(Method.GET)
                            .build()
            );

            fileInfos.add(new FileInfo(item.objectName(), presignedUrl));
        }

        return fileInfos;
    }

}
