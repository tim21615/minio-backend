package com.example.minio.service;

import com.example.minio.dto.FileInfo;
import com.example.minio.dto.GetPresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioService minioService;

    public GetPresignedUrlResponse getPresignedUrlResponse(String fileName) throws Exception {
        String presignedUrl = minioService.getPresignedUrl(fileName);

        return new GetPresignedUrlResponse(fileName, presignedUrl);
    }

    public FileInfo getFileUrl(String fileName) throws Exception {
        String fileUrl = minioService.getFileUrl(fileName);

        return new FileInfo(fileName, fileUrl);
    }

    public List<FileInfo> listFileInfos() throws Exception {
        return minioService.listObjectUrls();
    }
}
