package com.example.minio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPresignedUrlResponse {

    private String fileName;

    private String presignedUrl;
}
