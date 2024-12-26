package com.example.minio.controller;

import com.example.minio.dto.FileInfo;
import com.example.minio.dto.GetPresignedUrlResponse;
import com.example.minio.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/getPresignedUrl")
    public GetPresignedUrlResponse getPresignedUrl(@RequestParam String fileName) throws Exception {
        return fileService.getPresignedUrlResponse(fileName);
    }

    @GetMapping("/getFileUrl")
    public FileInfo getFileUrl(@RequestParam String fileName) throws Exception {
        return fileService.getFileUrl(fileName);
    }

    @GetMapping("/listFileInfos")
    public List<FileInfo> listFileInfos() throws Exception {
        return fileService.listFileInfos();
    }

}
