package com.laptrinhb2a.springboot.mailsender.controller;

import com.laptrinhb2a.springboot.mailsender.modal.response.FileUploadResponse;
import com.laptrinhb2a.springboot.mailsender.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadFileController {

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources");

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/image")
    public ResponseEntity<FileUploadResponse> uploadImage(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(uploadFileService.uploadImage(image));
    }

    @GetMapping("/video")
    public ResponseEntity<FileUploadResponse> uploadVideo(@RequestParam MultipartFile video) throws IOException {
        return ResponseEntity.ok(uploadFileService.uploadVideo(video));
    }

    @PostMapping("/multi-file")
    public ResponseEntity<List<FileUploadResponse>> uploadMultiFile(@RequestParam MultipartFile[] files) throws Exception {
        return ResponseEntity.ok(uploadFileService.uploadMultiImage(files));
    }


}
